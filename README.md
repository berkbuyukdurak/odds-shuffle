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

