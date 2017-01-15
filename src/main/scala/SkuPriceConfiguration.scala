class UnknownStockItemException extends RuntimeException

object SkuPriceConfiguration {
  def apply(skuPriceList: Map[StockItem, Float]): SkuPriceConfiguration = {
    new SkuPriceConfiguration(skuPriceList)
  }
}

class SkuPriceConfiguration(private val skuPriceList: Map[StockItem, Float]) {
  def getItemPrice(sku: StockItem): Float = {
    val skuConfigurationExists: Boolean = skuPriceList.exists(_._1 == sku)
    if (!skuConfigurationExists) {
      throw new UnknownStockItemException()
    }
    skuPriceList(sku)
  }
}