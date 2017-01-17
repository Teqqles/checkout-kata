object Checkout {
  def apply(priceConfiguration: PriceConfiguration, runningTotal: Float) = {
    new Checkout(priceConfiguration: PriceConfiguration, runningTotal)
  }
}

class Checkout(private val priceConfiguration: PriceConfiguration,
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
