import org.mockito.Mockito.{times, verify, when}
import org.scalatest.{Matchers, WordSpec}
import org.scalatest.mockito.MockitoSugar

class CheckoutSpec extends WordSpec with Matchers with MockitoSugar {

  val priceConfigurationMock = mock[SkuPriceConfiguration]
  val alternativePriceConfigurationMock = mock[SkuPriceConfiguration]
  val checkout = new Checkout(priceConfigurationMock)

  when(priceConfigurationMock.getItemPrice(A)) thenReturn 50
  when(priceConfigurationMock.getItemPrice(B)) thenReturn 30
  when(priceConfigurationMock.getItemPrice(C)) thenReturn 20
  when(priceConfigurationMock.getItemPrice(D)) thenReturn 15
  when(alternativePriceConfigurationMock.getItemPrice(A)) thenReturn 51

  "the checkout" should {
    "have zero total when no items scanned" in {
      checkout.getTotalShoppingBill should be(0f)
    }
    "scan one item at a time, adjusting the total" in {
      checkout
        .scan(A)
        .getTotalShoppingBill should be(50f)
      verify(priceConfigurationMock, times(1)).getItemPrice(A)
    }
    "adjust the total for additional items" in {
      checkout
        .scan(A)
        .scan(A)
        .getTotalShoppingBill should be(100f)
      verify(priceConfigurationMock, times(3)).getItemPrice(A)
    }
    "allow for different types of item" in {
      checkout
        .scan(A)
        .scan(B)
        .getTotalShoppingBill should be(80f)
      verify(priceConfigurationMock, times(4)).getItemPrice(A)
      verify(priceConfigurationMock, times(1)).getItemPrice(B)
    }
    "round the total to two significant figures" in {
      val deliberatelyInaccuratePrice = 1.255f
      val correctedInaccuratePrice = 1.26f
      when(priceConfigurationMock.getItemPrice(C)) thenReturn deliberatelyInaccuratePrice
      checkout
        .scan(C)
        .getTotalShoppingBill should be(correctedInaccuratePrice)
      verify(priceConfigurationMock, times(1)).getItemPrice(C)
    }
    "be able to use different price configurations per session" in {
      val checkout = new Checkout(alternativePriceConfigurationMock)
      checkout.scan(A).getTotalShoppingBill should be(51f)
      verify(alternativePriceConfigurationMock, times(1)).getItemPrice(A)
    }
  }
}
