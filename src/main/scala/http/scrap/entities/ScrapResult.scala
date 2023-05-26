package http.scrap.entities

import java.time.Instant

case class ScrapResult(
  id: String = java.util.UUID.randomUUID.toString,
  scrapRequestId: String,
  price:String,
  createdAt:Long = Instant.now().getEpochSecond
)
