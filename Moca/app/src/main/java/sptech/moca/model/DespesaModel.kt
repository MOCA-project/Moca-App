package sptech.moca.model

data class DespesaModel(
    val data: String,
    val descricao: String,
    val idCliente: Long,
    val idReceita: Long,
    val idTipoReceita: String,
    val paid: Boolean,
    val parcelada: Boolean,
    val valor: Double
)
