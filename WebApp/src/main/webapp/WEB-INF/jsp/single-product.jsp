<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="en">
  <head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>eElectronics - HTML eCommerce Template</title>
    
    <!-- Google Fonts -->
    <link href='http://fonts.googleapis.com/css?family=Titillium+Web:400,200,300,700,600' rel='stylesheet' type='text/css'>
    <link href='http://fonts.googleapis.com/css?family=Roboto+Condensed:400,700,300' rel='stylesheet' type='text/css'>
    <link href='http://fonts.googleapis.com/css?family=Raleway:400,100' rel='stylesheet' type='text/css'>
    
    <!-- Bootstrap -->
    <link rel="stylesheet" href="http://maxcdn.bootstrapcdn.com/bootstrap/3.2.0/css/bootstrap.min.css">
    
    <!-- Font Awesome -->
    <link rel="stylesheet" href="http://maxcdn.bootstrapcdn.com/font-awesome/4.3.0/css/font-awesome.min.css">
    
    <!-- Custom CSS -->
    <link rel="stylesheet" href="../../css/owl.carousel.css">
    <link rel="stylesheet" href="../../css/style.css">
    <link rel="stylesheet" href="../../css/responsive.css">

    <!-- HTML5 shim and Respond.js for IE8 support of HTML5 elements and media queries -->
    <!-- WARNING: Respond.js doesn't work if you view the page via file:// -->
    <!--[if lt IE 9]>
      <script src="https://oss.maxcdn.com/html5shiv/3.7.2/html5shiv.min.js"></script>
      <script src="https://oss.maxcdn.com/respond/1.4.2/respond.min.js"></script>
    <![endif]-->
  </head>
  <body>

  <div class="header-area">
      <div class="container">
          <div class="row">
              <div class="col-md-8">
                  <div class="user-menu">
                      <ul>
                          <li><a href="#"><i class="fa fa-user"></i>My Account</a></li>
                          <li><a href="cart"><i class="fa fa-shopping-cart"></i>Cart</a></li>
                      </ul>
                  </div>
              </div>

              <div class="col-md-4">
                  <div class="header-right">
                      <ul class="list-unstyled list-inline">
                          <li class="dropdown dropdown-small">
                              <a data-toggle="dropdown" data-hover="dropdown" class="dropdown-toggle" href="#"><span class="key">currency :</span><span class="value">UAH</span><b class="caret"></b></a>
                              <ul class="dropdown-menu">
                                  <li><a href="#">UAH</a></li>
                                  <li><a href="#">USD</a></li>
                              </ul>
                          </li>

                          <li class="dropdown dropdown-small">
                              <a data-toggle="dropdown" data-hover="dropdown" class="dropdown-toggle" href="#"><span class="key">language :</span><span class="value">English </span><b class="caret"></b></a>
                              <ul class="dropdown-menu">
                                  <li><a href="#">English</a></li>
                                  <li><a href="#">Russian</a></li>
                              </ul>
                          </li>
                      </ul>
                  </div>
              </div>
          </div>
      </div>
  </div> <!-- End header area -->

  <div class="site-branding-area">
      <div class="container">
          <div class="row">
              <div class="col-sm-6">
                  <div class="logo">
                      <h1><a href="index">i<span>Shop</span></a></h1>
                  </div>
              </div>

              <div class="col-sm-6">
                  <div class="shopping-item">
                      <a href="cart">Cart - <span class="cart-amunt">$0</span> <i class="fa fa-shopping-cart"></i> <span class="product-count">0</span></a>
                  </div>
              </div>
          </div>
      </div>
  </div> <!-- End site branding area -->

  <div class="mainmenu-area">
      <div class="container">
          <div class="row">
              <div class="navbar-header">
                  <button type="button" class="navbar-toggle" data-toggle="collapse" data-target=".navbar-collapse">
                      <span class="sr-only">Toggle navigation</span>
                      <span class="icon-bar"></span>
                      <span class="icon-bar"></span>
                      <span class="icon-bar"></span>
                  </button>
              </div>
              <div class="navbar-collapse collapse">
                  <ul class="nav navbar-nav">
                      <li><a href="index">Home</a></li>
                      <li><a href="shop">iPhone</a></li>
                      <li><a href="#">Accessories</a></li>
                  </ul>
              </div>
          </div>
      </div>
  </div> <!-- End mainmenu area -->

    <div class="single-product-area">
        <div class="zigzag-bottom"></div>
        <div class="container">
            <div class="row">
                <div class="col-md-12">
                    <div class="product-content-right">
                        <div class="product-breadcroumb">
                            <a href="">Home</a>
                            <a href="">iPhone</a>
                            <a href="">Sony Smart TV - 2015</a>
                        </div>
                        
                        <div class="row">
                            <div class="col-sm-6">
                                <div class="product-images">
                                    <div class="product-main-img">
                                        <img src="../../img/product-2.jpg" alt="">
                                    </div>
                                    
                                    <div class="product-gallery">
                                        <img src="../../img/product-thumb-1.jpg" alt="">
                                        <img src="../../img/product-thumb-2.jpg" alt="">
                                        <img src="../../img/product-thumb-3.jpg" alt="">
                                        <img src="../../img/product-thumb-4.jpg" alt="">
                                    </div>
                                </div>
                            </div>
                            
                            <div class="col-sm-6">
                                <div class="product-inner">
                                    <h2 class="product-name">Sony Smart TV - 2015</h2>
                                    <div class="product-inner-price">
                                        <ins>$700.00</ins> <del>$800.00</del>
                                    </div>    
                                    
                                    <form action="" class="cart">
                                        <div class="quantity">
                                            <input type="number" size="4" class="input-text qty text" title="Qty" value="1" name="quantity" min="1" step="1">
                                        </div>
                                        <button class="add_to_cart_button" type="submit">Add to cart</button>
                                    </form>
                                    
                                    <div role="tabpanel">
                                        <ul class="product-tab" role="tablist">
                                            <li role="presentation" class="active"><a href="#home" aria-controls="home" role="tab" data-toggle="tab">Description</a></li>
                                            <li role="presentation"><a href="#profile" aria-controls="profile" role="tab" data-toggle="tab">Reviews</a></li>
                                        </ul>
                                        <div class="tab-content">
                                            <div role="tabpanel" class="tab-pane fade in active" id="home">
                                                <h2>Product Description</h2>  
                                                <p>Lorem ipsum dolor sit amet, consectetur adipiscing elit. Nam tristique, diam in consequat iaculis, est purus iaculis mauris, imperdiet facilisis ante ligula at nulla. Quisque volutpat nulla risus, id maximus ex aliquet ut. Suspendisse potenti. Nulla varius lectus id turpis dignissim porta. Quisque magna arcu, blandit quis felis vehicula, feugiat gravida diam. Nullam nec turpis ligula. Aliquam quis blandit elit, ac sodales nisl. Aliquam eget dolor eget elit malesuada aliquet. In varius lorem lorem, semper bibendum lectus lobortis ac.</p>

                                                <p>Mauris placerat vitae lorem gravida viverra. Mauris in fringilla ex. Nulla facilisi. Etiam scelerisque tincidunt quam facilisis lobortis. In malesuada pulvinar neque a consectetur. Nunc aliquam gravida purus, non malesuada sem accumsan in. Morbi vel sodales libero.</p>
                                            </div>
                                            <div role="tabpanel" class="tab-pane fade" id="profile">
                                                <h2>Reviews</h2>
                                                <div class="submit-review">
                                                    <p><label for="name">Name</label> <input name="name" type="text"></p>
                                                    <p><label for="email">Email</label> <input name="email" type="email"></p>
                                                    <div class="rating-chooser">
                                                        <p>Your rating</p>

                                                        <div class="rating-wrap-post">
                                                            <i class="fa fa-star"></i>
                                                            <i class="fa fa-star"></i>
                                                            <i class="fa fa-star"></i>
                                                            <i class="fa fa-star"></i>
                                                            <i class="fa fa-star"></i>
                                                        </div>
                                                    </div>
                                                    <p><label for="review">Your review</label> <textarea name="review" id="" cols="30" rows="10"></textarea></p>
                                                    <p><input type="submit" value="Submit"></p>
                                                </div>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>                    
                </div>
            </div>
        </div>
    </div>


  <div class="footer-top-area">
      <div class="zigzag-bottom"></div>
      <div class="container">
          <div class="row">
              <div class="col-md-3 col-sm-6">
                  <div class="footer-about-us">
                      <h2>i<span>Shop</span></h2>
                      <p>Many of us prefer online stores, because this is the best way to save time and money. If you are interested in buying electronics or just look for an accessory for your mobile device or a useful gadget. Great online store "iShop" just offers you this opportunity.</p>
                      <div class="footer-social">
                          <a href="#" target="_blank"><i class="fa fa-facebook"></i></a>
                          <a href="#" target="_blank"><i class="fa fa-vk"></i></a>
                          <a href="#" target="_blank"><i class="fa fa-youtube"></i></a>
                      </div>
                  </div>
              </div>

              <div class="col-md-3 col-sm-6">
                  <div class="footer-menu">
                      <h2 class="footer-wid-title">User Navigation</h2>
                      <ul>
                          <li><a href="#">My account</a></li>
                          <li><a href="#">Cart</a></li>
                          <li><a href="#">Home</a></li>
                          <li><a href="#">iPhone</a></li>
                          <li><a href="#">Accessories</a></li>
                          <li><a href="#">Contact</a></li>
                      </ul>
                  </div>
              </div>

              <div class="col-md-6 col-sm-6">
                  <div class="footer-newsletter">
                      <h2 class="footer-wid-title">Newsletter</h2>
                      <p>Sign up to our newsletter and get exclusive deals you wont find anywhere else straight to your inbox!</p>
                      <div class="newsletter-form">
                          <form action="#">
                              <input type="email" placeholder="Type your email">
                              <input type="submit" value="Subscribe">
                          </form>
                      </div>
                  </div>
              </div>
          </div>
      </div>
  </div> <!-- End footer top area -->

  <div class="footer-bottom-area">
      <div class="container">
          <div class="row">
              <div class="col-md-8">
                  <div class="copyright">
                      <p>All Rights Reserved. Coded by itCentre Team&trade; 2017 &copy;</p>
                  </div>
              </div>

              <div class="col-md-4">
                  <div class="footer-card-icon">
                      <i class="fa fa-cc-mastercard"></i>
                      <i class="fa fa-cc-visa"></i>
                  </div>
              </div>
          </div>
      </div>
  </div> <!-- End footer bottom area -->
   
    <!-- Latest jQuery form server -->
    <script src="https://code.jquery.com/jquery.min.js"></script>
    
    <!-- Bootstrap JS form CDN -->
    <script src="http://maxcdn.bootstrapcdn.com/bootstrap/3.2.0/js/bootstrap.min.js"></script>
    
    <!-- jQuery sticky menu -->
    <script src="../../js/owl.carousel.min.js"></script>
    <script src="../../js/jquery.sticky.js"></script>
    
    <!-- jQuery easing -->
    <script src="../../js/jquery.easing.1.3.min.js"></script>
    
    <!-- Main Script -->
    <script src="../../js/main.js"></script>
  </body>
</html>