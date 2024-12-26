# Odds Shuffle

## Proje Genel Bakışı
Bu proje, kullanıcıların maçlara bahis yapabileceği ve kuponlar üzerinden birden fazla bahis oynayabileceği bir canlı bahis sistemi modellemektedir. Sistem, dinamik oran güncellemeleri, geçmiş oran takibi ve kupon yönetimi gibi özelliklere sahiptir.


## Entity Relationship Özeti

1. Bet (Bahis): Tek bir bahis işlemini temsil eder ve bir maça ve kupona bağlıdır.
2. Coupon (Kupon): Kullanıcı tarafından oynanan birden fazla bahisi içeren bir oturumu temsil eder.
3. Match (Maç): Lig, takımlar ve dinamik oranları içeren bir spor maçını temsil eder.
4. OddsHistory (Oran Geçmişi): Belirli bir maç için oran değişikliklerini zaman içinde takip eder.

| Varlık         | İlişki Tipi             | Bağlantılı Varlık | Açıklama                                  |
|-----------------|-------------------------|-------------------|------------------------------------------|
| `Bet`          | `@ManyToOne`           | `Coupon`          | Her bahis bir kupona aittir.             |
| `Bet`          | `@ManyToOne`           | `Match`           | Her bahis bir maça bağlıdır.             |
| `Coupon`       | `@OneToMany`           | `Bet`             | Bir kupon birden fazla bahis içerir.     |
| `OddsHistory`  | `@ManyToOne`           | `Match`           | Belirli bir maç için oran değişikliklerini izler. |

## API Endpointleri

### Maç Ekleme
- **Endpoint:** `POST /api/matches`
- **Açıklama:** Sisteme yeni bir maç ekler.
- **Request Body:**
    ```json
    {
    "league": "Premier League",
    "homeTeam": "Manchester United",
    "awayTeam": "Chelsea",
    "homeOdds": 1.75,
    "drawOdds": 3.5,
    "awayOdds": 2.1,
    "startTime": "2024-12-30T15:30:00"
    }
  
- Response:
    ```json
    {
      "id": "UUID",
      "league": "Premier League",
      "homeTeam": "Manchester United",
      "awayTeam": "Chelsea",
      "homeOdds": 1.75,
      "drawOdds": 3.5,
      "awayOdds": 2.1,
      "startTime": "2024-12-30T15:30:00"
    }
  
- **HTTP Status:** 201 Created

### Kupon Oluşturma
- **Endpoint:** POST /api/coupons
- **Açıklama:** Yeni bir kupon oluşturur.
- **Request Body:**
    ```json
    {
      "bets": [
        {
          "match": {
            "matchId": "550e8400-e29b-41d4-a716-446655440000"
          },
          "betType": "HOME_WIN"
        },
        {
          "match": {
            "matchId": "550e8400-e29b-41d4-a716-446655440001"
          },
          "betType": "DRAW"
        }
      ]
    }

- **Response:**
    ```json
    {
      "couponId": "UUID",
      "couponTime": "2024-12-26T15:00:00",
      "isExpired": false,
      "bets": [
        {
          "betId": "UUID",
          "matchId": "UUID",
          "oddsAtPlacement": 1.75,
          "betType": "HOME_WIN",
          "status": "PENDING"
        },
        {
          "betId": "UUID",
          "matchId": "UUID",
          "oddsAtPlacement": 3.5,
          "betType": "DRAW",
          "status": "PENDING"
        }
      ]
    }
  
- **HTTP Status:** 201 Created

### Kupon Detayı
- **Endpoint:** GET /api/coupons/{couponId}
- **Açıklama:** Belirli bir kuponun detaylarını getirir.
- **Response:**
    ```json
    {
      "couponId": "UUID",
      "couponTime": "2024-12-26T15:00:00",
      "isExpired": false,
      "bets": [
        {
          "betId": "UUID",
          "matchId": "UUID",
          "oddsAtPlacement": 1.75,
          "betType": "HOME_WIN",
          "status": "PENDING"
        },
        {
          "betId": "UUID",
          "matchId": "UUID",
          "oddsAtPlacement": 3.5,
          "betType": "DRAW",
          "status": "PENDING"
        }
      ]
    }
  
- **HTTP Status:** 200 OK

## Kullanılan Teknolojiler
- **Backend:** Spring Boot 3.x, Java 17
- **Veritabanı:** H2 Database
- **Canlı İletişim:** WebSocket
- **Container:** Docker

## Docker Kurulumu ve Çalıştırma
1. Docker ve docker-compose kurulu olduğundan emin olun.
2. Projeyi build edin:
    ```
    mvn clean package
    ```
3. Docker imajını oluşturun ve başlatın:
    ```
    docker-compose up --build
    ```
4. Uygulamaya tarayıcı üstünden erişin:
    ```
    http://localhost:8080/
    ```

### index.html'e Erişim
- **Konum:** src/main/resources/static/index.html
- **Erişim adresi:** http://localhost:8080/


## Notlar
- Kuponlar, oluşturuldukları andan itibaren belirli bir süre sonra (coupon.timeout) otomatik olarak EXPIRED durumuna geçer
- Tüm işlemler Docker ile containerize edilmiştir ve basit bir şekilde kurulup çalıştırılabilir.