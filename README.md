💡 Demo: Aplicación Web con Azure Custom Vision y Spring Boot
🎯 Objetivo
Diseñar e implementar una aplicación web sencilla que integre inteligencia artificial con análisis de imágenes mediante Azure Custom Vision, utilizando Java con Spring Boot, desplegada en Azure App Service. Ideal para mostrar en charlas y demos sobre desarrollo cloud e inteligencia artificial.

🧩 Arquitectura del Sistema
| Componente | Descripción | 
| Microservicio API | Backend Java con Spring Boot que consume el servicio de visión | 
| Frontend Web | Interfaz HTML + JS para subir imágenes y mostrar resultados | 
| Azure Custom Vision | Servicio de IA para clasificación de imágenes entrenado previamente | 
| Azure App Service | Hosting de la aplicación web completa | 

🧠 Paso 1: Preparar Azure Custom Vision
- Ingresar a https://www.customvision.ai
- Crear un nuevo proyecto
- Tipo: Clasificación multiclase
- Dominio: General o específico
- Subir imágenes y etiquetarlas
- Entrenar el modelo
- Obtener:
- Endpoint de predicción
- Clave de predicción
- ID del proyecto
- Nombre de la iteración

🛠️ Paso 2: Backend con Spring Boot

Estructura de Carpetas
vision-service/
├── src/main/java/com/demo/
│   ├── VisionServiceApplication.java
│   ├── controller/VisionController.java
│   ├── service/VisionService.java
│   └── model/PredictionResult.java + Prediction.java
├── src/main/resources/
│   ├── application.properties
│   └── static/index.html
├── pom.xml


application.properties
customvision.endpoint=https://<region>.api.cognitive.microsoft.com
customvision.key=TU_CLAVE
customvision.projectId=ID_DEL_PROYECTO
customvision.iterationName=NombreIteracion
server.port=8080







