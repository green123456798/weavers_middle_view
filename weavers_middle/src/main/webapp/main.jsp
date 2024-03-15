<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib tagdir="/WEB-INF/tags" prefix="common"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<!DOCTYPE html>
<html lang="ko">
<head>
<title>WeaveGlow - Main</title>
<common:head />

<style>
.carousel {
  position: relative;
  overflow: hidden;
}
.carousel-container {
  display: flex;
  transition: transform 0.5s ease;
}
.carousel-item {
  flex: 0 0 auto;
}
.carousel-item img {
  width: 100%;
  height: auto;
}
.carousel-control {
  position: absolute;
  top: 50%;
  transform: translateY(-50%);
  background: transparent;
  border: none;
  color: white;
  font-size: 2em;
  cursor: pointer;
}
.carousel-control.prev {
  left: 10px;
}
.carousel-control.next {
  right: 10px;
}
</style>

<style>
  .carousel-container {
    height: 300px; /* 원하는 높이 값으로 변경하세요 */
    overflow: hidden; /* 내용이 넘칠 경우를 대비해 오버플로우를 숨깁니다 */
    position: relative; /* 내부 컨텐츠를 정확히 배치하기 위해 상대적인 위치 지정 */
  }

  .carousel-item img {
    height: 100%; /* 이미지의 높이를 부모 요소의 높이에 맞게 설정합니다 */
    width: auto; /* 이미지의 가로 너비를 자동으로 조정합니다 */
    display: block; /* 이미지를 블록 요소로 표시하여 다른 내용과 겹치지 않도록 합니다 */
    margin: 0 auto; /* 이미지를 가운데 정렬합니다 */
  }
</style>

<style>
  .carousel-control {
    background-color: #007bff; /* 버튼의 배경색을 파란색으로 설정합니다. */
    color: #fff; /* 버튼의 글자색을 흰색으로 설정합니다. */
    border: none; /* 버튼의 테두리를 없앱니다. */
    padding: 10px 15px; /* 버튼의 내부 여백을 지정합니다. */
    cursor: pointer; /* 마우스 커서를 포인터로 변경하여 클릭 가능성을 나타냅니다. */
    border-radius: 5px; /* 버튼의 모서리를 둥글게 만듭니다. */
    font-size: 18px; /* 버튼의 글자 크기를 설정합니다. */
  }

  .carousel-control:hover {
    background-color: #0056b3; /* 마우스를 올렸을 때 버튼의 배경색을 진한 파란색으로 변경합니다. */
  }
</style>

</head>
<body>
	<common:header />
	<main class="site-main">
	
	<!-- 	<section class="section-margin calc-60px"> -->
			<!-- 캐러쉘  -->

<div class="carousel">
  <div class="carousel-container"  >
    <div class="carousel-item " >
      <img src="uploadimg/carousel/slide2.jpg" alt="Slide 1">
    </div>
    <div class="carousel-item active">
      <img src="uploadimg/carousel/slide1.jpg" alt="Slide 2">
    </div>
    <div class="carousel-item">
      <img src="uploadimg/carousel/slide3.jpg" alt="Slide 3">
    </div>
  </div>
  <button class="carousel-control prev" >&lt;</button>
  <button class="carousel-control next">&gt;</button>
</div>

			<!-- ================ 추천 상품 ================= -->
			<div class="container">
				<div class="section-intro pb-60px">
					<p>Best 찜 상품</p>
					<h2>
						<span class="section-intro__style">추천 상품</span>
					</h2>
				</div>
				<div class="row">
					<c:if test="${fn:length(wdatas) <= 0}">
						상품이 없습니다.
					</c:if>
					<c:if test="${fn:length(wdatas) > 0}">
						<!-- <div class="row"> -->
						<c:forEach var='data' items='${wdatas}'>
							<%--  <c:out value="${data.ppk}" /> --%>
							<!-- <div class="row"></div> -->

							<div class="col-md-6 col-lg-4 col-xl-3">
								<div class="card text-center card-product"
									data-product-pk="${data.ppk}">
									<div class="card-product__img">
										<!-- 					<script>
  								  alert("data.ppk value: ${data.ppk}");
									</script> -->
										<a href="/productDetail?ppk=${data.ppk}"><img
											class="card-img" src="${data.img}" alt=""></a>

										<ul class="card-product__imgOverlay">
											<li>
												<button onclick="wishClick(${data.ppk},'${sessionMid}')"
													class="product-btn-${data.ppk}">
													<c:if test="${data.wish == 1}">
														<i class="ti-heart" style="color: red;"></i>
													</c:if>
													<c:if test="${data.wish == 0}">
														<i class="ti-heart" style="color: #fff;"></i>
													</c:if>
												</button>
											</li>
										</ul>
									</div>
									<div class="card-body">
										<h4 class="card-product__title">
											<a href="/productDetail?ppk=${data.ppk}">${data.pname}</a>
										</h4>
										<p class="card-product__price">
											<fmt:formatNumber value="${data.price}" currencyCode="KRW" />
											원
										</p>
									</div>
								</div>

							</div>
						</c:forEach>
						<!-- </div> -->
					</c:if>
				</div>
			</div>
		<!-- </section> -->
		<!-- ================ /추천 상품 ================= -->

		<!-- ================ 인기 상품 ================= -->
		<section class="section-margin calc-60px">
			<div class="container">
				<div class="section-intro pb-60px">
					<p>Best 판매 상품</p>
					<h2>
						<span class="section-intro__style">인기 상품</span>
					</h2>
				</div>
				<div class="owl-carousel owl-theme" id="bestSellerCarousel">

					<c:if test="${fn:length(sdatas) <= 0}">
						상품이 없습니다.
					</c:if>
					<c:if test="${fn:length(sdatas) > 0}">
						<!-- <div class="row"> -->
						<c:forEach var="data" items="${sdatas}">
							<div class="card text-center card-product"
								data-product-pk="${data.ppk}">
								<div class="card-product__img">
									<a href="/productDetail?ppk=${data.ppk}"><img
										class="card-img" src="${data.img}" alt="${data.img}번 상품사진"></a>
									<ul class="card-product__imgOverlay">
										<li>
											<button onclick="wishClick(${data.ppk},'${sessionMid}')"
												class="product-btn-${data.ppk}">
												<c:if test="${data.wish == 1}">
													<i class="ti-heart" style="color: red;"></i>
												</c:if>
												<c:if test="${data.wish == 0}">
													<i class="ti-heart" style="color: #fff;"></i>
												</c:if>
											</button>
										</li>
									</ul>
								</div>
								<div class="card-body">
									<h4 class="card-product__title">
										<a href="/productDetail?ppk=${data.ppk}">${data.pname}</a>
									</h4>
									<p class="card-product__price">
										<fmt:formatNumber value="${data.price}" currencyCode="KRW" />
										원
									</p>
								</div>
							</div>
						</c:forEach>
					</c:if>
				</div>
			</div>
		</section>
		<!-- ================ /인기 상품 ================= -->
	</main>

	<common:footer />
	<script type="text/javascript">
	  const container = document.querySelector('.carousel-container');
	  const prevBtn = document.querySelector('.prev');
	  const nextBtn = document.querySelector('.next');
	  const items = document.querySelectorAll('.carousel-item');
	  let currentIndex = 0;

	  function showSlide(index) {
	    container.style.transform = `translateX(-${index * 100}%)`;
	  }

	  function moveToNextSlide() {
	    currentIndex = (currentIndex + 1) % items.length;
	    showSlide(currentIndex);
	  }

	  function moveToPrevSlide() {
	    currentIndex = (currentIndex - 1 + items.length) % items.length;
	    showSlide(currentIndex);
	  }

	  nextBtn.addEventListener('click', moveToNextSlide);
	  prevBtn.addEventListener('click', moveToPrevSlide);
	</script>
</body>
</html>