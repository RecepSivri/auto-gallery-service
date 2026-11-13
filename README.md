# Oto Galeri

Java mülakat kartlarındaki (86 soru) her konuyu uygulamalı denemek için adım adım geliştirilen Spring Boot projesi.

- Stack: Spring Boot 4.1, Java 21, Maven, PostgreSQL (Docker Compose / Colima), Flyway
- Kod isimleri İngilizce; yorum, README ve deney notları Türkçe
- Çalışma modu: karma. İskelet ve tekrarlı kod Claude'dan, kritik parçalar Recep'ten

## Çalıştırma

```bash
colima start
docker compose up -d      # PostgreSQL
./mvnw spring-boot:run
```

Sağlık kontrolü: http://localhost:8090/actuator/health

## Adımlar

0. Kurulum, 1. İskelet/profiller/Flyway/HikariCP, 2. Domain modeli, 3. Spring çekirdeği,
4. REST + doğrulama + test, 5. Arama/performans, 6. SQL raporları, 7. Satış ve eşzamanlılık,
8. Güvenlik (Keycloak), 9. İleri test, 10. Fotoğraf ve async, 11. Redis + Resilience4j,
12. RabbitMQ outbox, 13. Prod hazırlığı

Deney notları: `docs/deneyler.md`
