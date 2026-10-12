# Proyecto de procesamiento de ventas - Entrega 3

## Descripción
Este proyecto está desarrollado en Java y permite procesar información de productos, vendedores y ventas para generar reportes en archivos CSV.

## Funcionalidades
- Generar archivos de productos y vendedores.
- Crear archivos de ventas para cada vendedor.
- Leer y procesar las ventas registradas.
- Calcular el total recaudado por vendedor.
- Calcular las cantidades vendidas por producto.
- Generar los reportes `reporte_vendedores.csv` y `reporte_productos.csv`.
- Validar la información de entrada para detectar errores.

## Archivos principales
- `GenerateInfoFiles.java`: genera archivos de prueba.
- `Producto.java`: representa los productos.
- `Vendedor.java`: representa los vendedores.
- `main.java`: procesa las ventas y genera los reportes.

## Archivos de entrada
- `productos.txt`: contiene el identificador, nombre y precio de cada producto.
- `vendedores.txt`: contiene los datos de los vendedores.
- Carpeta `ventas`: contiene los archivos de ventas de cada vendedor.

## Archivos de salida
- `reporte_vendedores.csv`: presenta el total recaudado por cada vendedor.
- `reporte_productos.csv`: presenta los productos ordenados por cantidad vendida.

## Ejecución
1. Importar el proyecto en Eclipse.
2. Ejecutar `GenerateInfoFiles.java` para generar los archivos de prueba.
3. Ejecutar `main.java` para procesar las ventas.
4. Revisar los archivos CSV generados.

## Tecnologías
- Java
- Eclipse IDE
- GitHub
