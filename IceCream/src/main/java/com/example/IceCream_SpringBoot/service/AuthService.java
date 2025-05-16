package com.example.IceCream_SpringBoot.service;

import com.example.IceCream_SpringBoot.model.User;
import com.example.IceCream_SpringBoot.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder; //Inyectamos el encriptador de contraseñas

    public boolean usuarioExiste(String usuario) {
        return userRepository.findByUsername(usuario).isPresent();
    }

    public boolean registrarNuevoUsuario(String usuario, String contrasena, String pin) {
        if (usuarioExiste(usuario)) {
            return false; // No permite registrar si el usuario ya existe
        }
        if (!pin.equals("admin123")) { 
            return false; //Solo registra si el PIN es correcto
        }

        //Encripta la contraseña antes de guardarla
        String hashedPassword = passwordEncoder.encode(contrasena);

        //Crea el usuario y lo guarda en la base de datos
        User user = new User(usuario, hashedPassword);
        userRepository.save(user);
        return true;
    }
}
