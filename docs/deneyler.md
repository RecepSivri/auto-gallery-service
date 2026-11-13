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

## Adım 2 deneyleri

### Deney 5: Lombok @Data tuzağı (Vehicle)
- Yaptım: `Vehicle`'a geçici olarak `@Data` ekle (equals/hashCode/toString otomatik gelir). `Sale` içinde de `@Data` varsa `vehicle.toString()` çağır; aracı bir `HashSet`'e koy, kaydedip (id atanınca) `contains` çağır.
- Beklenen:
- Gözlem (StackOverflowError mı? HashSet'te kayıp mı?):
- Mülakat cevabı (entity'de neden @Data/@EqualsAndHashCode kullanılmaz?):

### Deney 6: Plate değiştirilebilir olsaydı (HashSet)
- Yaptım: `Plate`'i geçici olarak setter'lı normal sınıfa çevir, HashSet'e ekle, değeri değiştir, `contains` çağır. Sonra record'a geri dön.
- Beklenen:
- Gözlem:
- Mülakat cevabı (immutable sınıf neden güvenli anahtar olur?):

### Deney 7: Enum ORDINAL ve STRING
- Yaptım: `status` alanını geçici olarak `@Enumerated(ORDINAL)` yap, çalıştır (validate ne diyor?). Geri al.
- Beklenen:
- Gözlem:
- Mülakat cevabı (neden STRING?):

### Deney 8: Persistence context (managed / detached)
- Yaptım: `VehicleRepositoryTest` içinde `em.clear()` satırını kaldır, aynı testi çalıştır; SQL log'unda `SELECT` var mı bak (`spring.jpa.show-sql=true`).
- Beklenen:
- Gözlem:
- Mülakat cevabı (1. seviye cache nedir, entity durumları nelerdir?):

## Adım 4'e ertelenenler (REST endpoint gelince)

### Deney 4: Havuz boyutu 2, timeout kısa
- Yaptım:
- Beklenen:
- Gözlem:
- Mülakat cevabı:
