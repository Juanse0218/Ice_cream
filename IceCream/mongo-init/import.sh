#!/bin/bash
echo "🏁 Importando datos a MongoDB..."
mongoimport \
  --db heladeria_db \
  --collection helados \
  --file /docker-entrypoint-initdb.d/helados.json \
  --jsonArray
echo "Datos importados correctamente."
