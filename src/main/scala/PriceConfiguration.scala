class UnknownStockItemException extends RuntimeException

object PriceConfiguration {
  def apply(skuPriceList: Map[StockItem, Float]): PriceConfiguration = {
    new PriceConfiguration(skuPriceList)
  }
}

class PriceConfiguration(private val priceList: Map[StockItem, Float]) {
  def getItemPrice(sku: StockItem): Float = {
    priceList.getOrElse(
      sku,
      throw new UnknownStockItemException()
    )
  }
}