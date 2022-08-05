#FROM adoptopenjdk:11-jre-openj9-bionic
#Imajımıza Open JDK yı kuruyoruz
FROM openjdk:11
#Projemizin jar haline getirilmiş yolunu gösteriyoruz. Ve jar ın ismini belirtiyoruz
ADD target/overcatch-backend-0.0.1-SNAPSHOT.jar docker-overcatch.jar
#Hangi portta çalıştığını belirtiyoruz
EXPOSE 8081
#Projemizi ayağa kaldıracak komutumuzu belirtiyoruz
ENTRYPOINT ["java","-jar","docker-overcatch.jar"]
