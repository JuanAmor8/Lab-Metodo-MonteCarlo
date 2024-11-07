---

# 🌟 Proyecto Monte Carlo 🌟

¡Bienvenido al README del proyecto Master-Worker-Client! Este repositorio contiene las instrucciones necesarias para compilar y ejecutar cada componente del sistema. A continuación, encontrarás todos los pasos para ponerlo en marcha. ¡Esperamos que disfrutes explorando y probando el proyecto! 🚀

## 📋 Requisitos previos

Para ejecutar este proyecto, asegúrate de tener instalado:
- **Java JDK 11+** 
- **Gradle**

---

## ⚙️ Compilación del Proyecto

Antes de ejecutar los servicios, compila el proyecto con los siguientes comandos.

1. Limpia y construye el proyecto:
   ```bash
   ./gradlew clean build
   ```

2. O, simplemente compila:
   ```bash
   ./gradlew build
   ```

---

## 🚀 Ejecución de los Componentes

El sistema está compuesto por tres aplicaciones principales: **Master**, **Worker** y **Client**. Ejecuta cada una con los siguientes comandos:

1. **Ejecutar Master**:
   ```bash
   java -jar master/build/libs/master.jar
   ```

2. **Ejecutar Worker**:
   ```bash
   java -jar worker/build/libs/worker.jar
   ```

3. **Ejecutar Client**:
   ```bash
   java -jar client/build/libs/client.jar
   ```

---

## 📝 Notas Adicionales

- **Configuración**: Asegúrate de revisar los archivos de configuración de cada componente antes de la ejecución.
- **Ejecución en paralelo**: Para el correcto funcionamiento, ejecuta Master, Worker y Client en ventanas separadas.

## 📬 Contacto

¿Tienes preguntas o comentarios? ¡Estamos aquí para ayudar!.
+57 321 521 2616
---

¡Gracias por confiar en nuestro proyecto! 🧩
