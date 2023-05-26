package http.scrap.entities

case class ScrapRequest(
  id: String = java.util.UUID.randomUUID.toString,
  url: String
)
