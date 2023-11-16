package sptech.moca.model

data class CartaoModel(
    val limite: Double,
    val utilizado: Double,
    val idCliente: Long,
    val idTipo: Long,
    val idCor: Long,
    val Bandeira: String,
    val apelido: String,
    val vencimento: String
)
