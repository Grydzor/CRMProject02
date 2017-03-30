<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="en">
  <head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>Оформление заказа | iShop</title>
      <%@include file="embedded/load-footer-libraries.jsp"%>
  </head>
  <body>

    <%@include file="embedded/menu.jsp"%>

    <div class="product-big-title-area">
        <div class="row">
            <div class="col-md-12">
                <div class="product-big-title text-center">
                    <h2>Доставка</h2>
                </div>
            </div>
        </div>
    </div>
    <div class="single-product-area">
        <div class="zigzag-bottom"></div>
        <div class="container" id="order-form">
            <div class="row">
                <div class="col-md-12">
                    <div class="product-content-right">
                        <div class="acc-info">
                            Уже покупали у нас?
                            <a data-toggle="collapse" href="#login-form-wrap">
                                Войти в аккаунт
                            </a>
                            <fieldset id="login-form-wrap" class="collapse">
                                <div class="inner-form">
                                    <p>
                                        <input type="text" placeholder="Электронная почта">
                                    </p>
                                    <p>
                                        <input type="password" placeholder="Пароль">
                                    </p>
                                    <p class="lost-pass">
                                        <a href="/recover">Забыли пароль?</a>
                                    </p>
                                    <p class="btn-login">
                                        <button class="btn-block" type="submit" onclick="logIn()">Войти</button>
                                    </p>
                                </div>
                            </fieldset>
                        </div>
                        <fieldset class="checkout" name="checkout">
                            <div id="customer_details" class="col2-set">
                                <div class="col-md-8">
                                    <h3>Детали доставки</h3>
                                    <p id="billing_first_name_field" class="form-row form-row-first validate-required">
                                        <input id="deliveryName" type="text" placeholder="Имя">
                                    </p>
                                    <p>
                                        <input id="deliveryPhone" type="text" placeholder="Телефон">
                                    </p>
                                    <p id="billing_city_field" class="form-row form-row-wide address-field validate-required" data-o_class="form-row form-row-wide address-field validate-required">
                                        <input id="deliveryCity" type="text" placeholder="Город">
                                    </p>
                                    <p>
                                        <input id="deliveryStreet" type="text" placeholder="Улица">
                                    </p>
                                    <p>
                                        <input id="deliveryHouseNumber" type="text" placeholder="Номер дома, квартиры (по-желанию)">
                                    </p>
                                </div>
                            </div>
                            <div class="acc-info">
                                Сохранить данные для будущих заказов?
                                <a data-toggle="collapse" href="#signup-form-wrap">
                                    Зарегистрироваться
                                </a>
                                <fieldset id="signup-form-wrap" class="collapse">
                                    <div class="inner-form">
                                        <p>
                                            <input id="regEmail" type="text" placeholder="Электронная почта">
                                        </p>
                                        <p>
                                            <input id="regPassword" type="password" value="" placeholder="Пароль">
                                        </p>
                                        <p>
                                            <input id="regConfirmPassword" type="password" value="" placeholder="Повторите пароль">
                                        </p>
                                        <p>
                                            Вы автоматически будете зарегистрированы при подтверждении заказа.
                                        </p>
                                    </div>
                                </fieldset>
                            </div>
                            <h3 id="payment_details">Ваш заказ</h3>
                            <div id="order_review" style="position: relative;">
                                <table class="shop_table">
                                    <thead>
                                        <tr>
                                            <th class="product-name">Товар</th>
                                            <th class="product-total">Итого</th>
                                        </tr>
                                    </thead>
                                    <tbody>
                                        <tr class="cart_item">
                                            <td class="product-name">
                                                Пробный товар <strong class="product-quantity">× 1</strong> </td>
                                            <td class="product-total">
                                                <span class="amount">£15.00</span>
                                            </td>
                                        </tr>
                                    </tbody>
                                    <tfoot>
                                        <tr class="cart-subtotal">
                                            <th>Стоимость товара</th>
                                            <td>
                                                <span class="amount">£15.00</span>
                                            </td>
                                        </tr>
                                        <tr class="shipping">
                                            <th>Доставка</th>
                                            <td>
                                                Бесплатная
                                                <input type="hidden" class="shipping_method" value="free_shipping" id="shipping_method_0" data-index="0" name="shipping_method[0]">
                                            </td>
                                        </tr>
                                        <tr class="order-total">
                                            <th>Общая стоимость</th>
                                            <td>
                                                <span class="amount">£15.00</span>
                                            </td>
                                        </tr>
                                    </tfoot>
                                </table>
                                <div id="payment">
                                    <h3 id="order_review_heading">Оплата</h3>
                                    <p>
                                        Список доступных способов оплаты
                                    </p>
                                </div>
                            </div>
                            <div class="form-row place-order">
                                <button type="submit" onclick="submitOrder()">
                                    Оформить заказ
                                </button>
                            </div>
                        </fieldset>
                    </div>
                </div>
            </div>
        </div>
    </div>

    <%@include file="embedded/footer.jsp"%>
    <%@include file="embedded/load-libraries.jsp"%>
  </body>
</html>