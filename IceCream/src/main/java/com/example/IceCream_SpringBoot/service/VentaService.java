package com.example.IceCream_SpringBoot.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.example.IceCream_SpringBoot.model.Cliente;
import com.example.IceCream_SpringBoot.model.HeladoDocument;
import com.example.IceCream_SpringBoot.model.ItemVenta;
import com.example.IceCream_SpringBoot.model.VentaDocument;
import com.example.IceCream_SpringBoot.repository.ClienteRepository;
import com.example.IceCream_SpringBoot.repository.HeladoRepository;
import com.example.IceCream_SpringBoot.repository.VentaRepository;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class VentaService {

    @Autowired
    private HeladoRepository heladoRepository;

    @Autowired
    private ClienteRepository clienteRepository;

    @Autowired
    private VentaRepository ventaRepository;

    @Transactional
    public boolean procesarVenta(
            List<String> nombresHelados,
            List<Integer> unidadesPorHelado,
            String metodoPago,
            double totalEnviado,
            String nombreCliente,
            String cedula,
            LocalDateTime fechaNacimiento,
            String telefono,
            String vendedor) {

        if (nombresHelados == null || unidadesPorHelado == null || nombresHelados.size() != unidadesPorHelado.size() || nombresHelados.isEmpty()) {
            System.err.println("Error: Las listas de helados y unidades no coinciden o están vacías.");
            return false;
        }

        List<ItemVenta> itemsDeVenta = new ArrayList<>();
        double totalCalculadoServidor = 0;

        // 1. Validar stock y calcular subtotal para cada helado
        for (int i = 0; i < nombresHelados.size(); i++) {
            String nombreHelado = nombresHelados.get(i);
            int unidadesVender = unidadesPorHelado.get(i);

            if (unidadesVender <= 0) {
                System.err.println("Error: Unidades a vender debe ser mayor a cero para " + nombreHelado);
                return false; // Cantidad invalida
            }

            Optional<HeladoDocument> optHelado = heladoRepository.findByNombreAndUbicacion(nombreHelado, "heladeria");

            if (optHelado.isEmpty()) {
                System.err.println("Error: Helado no encontrado - " + nombreHelado);
                return false; // Helado no encontrado
            }

            HeladoDocument helado = optHelado.get();
            if (helado.getUnidades() < unidadesVender) {
                System.err.println("Error: Stock insuficiente para " + nombreHelado + ". Solicitado: " + unidadesVender + ", Disponible: " + helado.getUnidades());
                return false; // Stock insuficiente
            }

            double precioUnitario = helado.getPrecio();
            double subTotal = precioUnitario * unidadesVender;
            totalCalculadoServidor += subTotal;

            // Guardamos el helado original para la referencia en ItemVenta
            // La actualizacion de stock se hara despues de validar todo.
            itemsDeVenta.add(new ItemVenta(helado, unidadesVender, precioUnitario, subTotal));
        }

        // 2. Aplicar recargo por metodo de pago
        if (metodoPago.equalsIgnoreCase("tarjeta")) {
            totalCalculadoServidor += totalCalculadoServidor * 0.03;
        }

        // 3. Validar contra el totalEnviado por el cliente
        if (Math.abs(totalEnviado - totalCalculadoServidor) > 0.01) {
            System.err.println("Error: Discrepancia en el total. Calculado por servidor: " + totalCalculadoServidor + ", Enviado por cliente: " + totalEnviado);
            return false;
        }

        // 4. Si todas las validaciones son correctas, se resta al inventario
        for (ItemVenta item : itemsDeVenta) {
            HeladoDocument heladoActualizar = heladoRepository.findById(item.getHelado().getId()).orElse(null);
            // Actualizamos el stock del helado
            if (heladoActualizar != null) {
                heladoActualizar.setUnidades(heladoActualizar.getUnidades() - item.getCantidad());
                heladoRepository.save(heladoActualizar);
            } else {
                // Por si acaso, aunque no deberia pasar :<
                System.err.println("Error critico: Helado no encontrado durante actualizacion de inventario: " + item.getHelado().getNombre());
                throw new RuntimeException("Error critico al actualizar inventario, helado no encontrado: " + item.getHelado().getNombre());
            }
        }

        // 5. Validar o crear cliente
        Cliente cliente;
        Cliente clienteExistente = clienteRepository.findByCedula(cedula);
        if (clienteExistente != null) {
            cliente = clienteExistente;
        } else {
            cliente = new Cliente(nombreCliente, cedula, fechaNacimiento.toLocalDate(), telefono);
            clienteRepository.save(cliente);
        }

        // 6. Registrar venta
        VentaDocument venta = new VentaDocument(
                itemsDeVenta,
                vendedor,
                cliente,
                metodoPago,
                totalCalculadoServidor,
                LocalDateTime.now());
        ventaRepository.save(venta);

        return true;
    }
}