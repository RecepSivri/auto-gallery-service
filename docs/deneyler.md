# Deney notları

Her "bilerek bozma" deneyi için: ne yaptım, ne bekledim, ne oldu, mülakatta nasıl anlatırım.

## Adım 1

### Deney 1: Auto-configuration raporu
- Yaptım: `./mvnw spring-boot:run -Dspring-boot.run.arguments=--debug`
- Bak: log'da `CONDITIONS EVALUATION REPORT`, `Positive matches` içinde `DataSourceAutoConfiguration`/Hikari, `Negative matches` içinde örneğin Mongo veya Kafka
- Beklenen:
- Gözlem:
- Mülakat cevabı (Spring Boot auto-configuration nasıl çalışır?):

### Deney 2: Flyway checksum hatası
- Yaptım: `V1__brand.sql` dosyasına bir yorum satırı ekle, uygulamayı çalıştır. Sonra geri al.
- Beklenen:
- Gözlem (hata mesajı):
- Mülakat cevabı (çalışmış migration neden değiştirilmez?):

## Adım 2'ye ertelenenler (Brand entity gelince)

### Deney 3: ddl-auto=update ve validate farkı
- Yaptım:
- Beklenen:
- Gözlem:
- Mülakat cevabı:

## Adım 4'e ertelenenler (REST endpoint gelince)

### Deney 4: Havuz boyutu 2, timeout kısa
- Yaptım:
- Beklenen:
- Gözlem:
- Mülakat cevabı:
