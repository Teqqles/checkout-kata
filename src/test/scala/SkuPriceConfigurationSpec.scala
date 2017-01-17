import org.scalatest.{Matchers, WordSpec}

class SkuPriceConfigurationSpec extends WordSpec with Matchers {

  def SkuPrices(): Map[StockItem, Float] = {
    Map(
      A -> 2.99f,
      B -> 3f,
      C -> 4f
    )
  }

  val standardStockPriceConfiguration = PriceConfiguration(SkuPrices())

  "SkuPriceConfiguration" should {
    "accept an empty stocklist" in {
      PriceConfiguration(Map())
    }
    "return a price for a given SKU" in {
      standardStockPriceConfiguration.getItemPrice(A) should be(2.99f)
      standardStockPriceConfiguration.getItemPrice(C) should be(4f)
    }
    "provide the latest price supplied for an SKU" in {
      val revisedStockPrice = A -> 6f
      val stockPriceConfiguration = PriceConfiguration(SkuPrices() + revisedStockPrice)
      stockPriceConfiguration.getItemPrice(A) should be(6)
    }
    "notify the caller that an item does not exist" in {
      val emptyPriceConfig = PriceConfiguration(Map())
      an[UnknownStockItemException] should be thrownBy emptyPriceConfig.getItemPrice(A)
      an[UnknownStockItemException] should be thrownBy standardStockPriceConfiguration.getItemPrice(D)
    }
  }
}
