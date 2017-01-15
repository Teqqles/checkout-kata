object Checkout {
  def apply(priceConfiguration: SkuPriceConfiguration, runningTotal: Float) = {
    new Checkout(priceConfiguration: SkuPriceConfiguration, runningTotal)
  }
}

class Checkout(private val priceConfiguration: SkuPriceConfiguration,
               private val shoppingBill: Float = 0f) {
  def scan(stockItem: StockItem): Checkout = {
    val itemPrice = priceConfiguration.getItemPrice(stockItem)
    Checkout(priceConfiguration, shoppingBill + itemPrice)
  }

  def getTotalShoppingBill: Float = {
    val roundToTwoDecimalPlaces: Float = Math.round(shoppingBill * 100) / 100f
    roundToTwoDecimalPlaces
  }
}
