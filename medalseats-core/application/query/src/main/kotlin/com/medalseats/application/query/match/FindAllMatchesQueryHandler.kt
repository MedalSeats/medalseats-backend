package com.medalseats.application.query.match
 
import com.unicamp.medalseats.match.Match
import com.unicamp.medalseats.match.MatchRepository
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.toImmutableList
 
class FindAllMatchesQueryHandler(
    private val matchRepository: MatchRepository
) {
 
    // Função para lidar com a query de encontrar todas as partidas
    suspend fun handle(query: FindAllMatchesQuery) =
        FindAllMatchesQueryProjection(
            with(query) {
                // Buscar todas as partidas com offset e limite
                matchRepository.findAll(
                    offset = offset,
                    limit = limit
                )
            }.map { match ->
                // Converter cada partida para uma projeção
                match.toProjection()
            }.toImmutableList() // Converter a lista para uma lista imutável
        )
 
    // Função para converter uma partida para uma projeção
    private fun Match.toProjection() = FindAllMatchesQueryProjection.Match(
        id = id.toUUID(), // Converter ID para UUID
        title = title, // Título da partida
        subtitle = subtitle, // Subtítulo da partida
        description = description, // Descrição da partida
        stadium = FindAllMatchesQueryProjection.Match.Stadium(
            name = stadium.name, // Nome do estádio
            imageUrl = stadium.imageUrl // URL da imagem do estádio
        ),
        bannerUrl = bannerUrl, // URL do banner
        date = date, // Data da partida
        geolocation = FindAllMatchesQueryProjection.Match.Geolocation(
            latitude = geolocation.latitude, // Latitude da geolocalização
            longitude = geolocation.longitude // Longitude da geolocalização
        ),
        iconUrl = iconUrl, // URL do ícone
        availableTickets = availableTickets.toProjection() // Converter os ingressos disponíveis para uma projeção
    )
 
    // Função para converter uma lista imutável de ingressos para uma projeção
    private fun ImmutableList<Match.Ticket>.toProjection(): ImmutableList<FindAllMatchesQueryProjection.Match.Ticket> =
        this.map { it.toProjection() }.toImmutableList() // Mapear e converter cada ingresso
 
    // Função para converter um ingresso para uma projeção
    private fun Match.Ticket.toProjection() = FindAllMatchesQueryProjection.Match.Ticket(
        category = this.category, // Categoria do ingresso
        price = this.price // Preço do ingresso
    )
}
