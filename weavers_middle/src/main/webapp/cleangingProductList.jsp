<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib tagdir="/WEB-INF/tags" prefix="common"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>

<!DOCTYPE html>
<html lang="ko">
<head>
<title>WeaveGlow - cleangingProductList</title>
<common:head />
<style type="text/css">


.grid-container {
	display: flex; /* 내부 요소를 수평으로 배치하기 위해 flex 사용 */
	flex-wrap: wrap; /* 요소들이 넘치면 다음 줄로 이동하도록 설정 */
	justify-content: center; /* 요소들을 수평 가운데 정렬 */
	padding: 0;
	list-style: none; /* 리스트 스타일 제거 */
	height: 80px; /* 박스의 높이 */
}

.ctgr_box {
	width: 275px; /* 박스의 너비 */
	display: flex; /* 내부 요소를 가로로 배열하기 위해 flex 사용 */
	align-items: center; /* 내부 요소를 수직 가운데 정렬 */
	justify-content: center; /* 내부 요소를 수평 가운데 정렬 */
	text-decoration: none; /* 링크에 밑줄 제거 */
	color: black; /* 링크 색상 */
	font-size: 20px;
	text-align: center;
}

.grid-container li {
	display: flex;
	justify-content: center;
	align-items: center;
	height: 100%;
}

.ctgr_box.active {
	font-weight: bold; /* 선택된 항목에 대한 강조 표시 */
}

.list-area ul {
    display: flex; /* 요소들을 가로로 배열 */
    /* justify-content: space-between; */
     justify-content: center; /* 가운데 정렬 */
    padding: 0; /* 기본 패딩 제거 */
    list-style: none; /* 목록 스타일 제거 */
}


</style>

</head>
<body>
	<common:header />
	<common:banner bannerText='클렌징' />

	<!-- ================ 내용 ================= -->
	<br>
	<section class="product-depth p_ver">
                        <div class="depth-list-wrap">
                            <div class="list-area">
                                <ul>
                                    <li><a href="/ctgr/subcategory_product_list.do?i_sCategorycd1=L01&amp;i_sCategorynm1=기초&amp;i_sCategorycd2=L01M02&amp;i_sCategorynm2=클렌징" id="" class="ctgr_box active" style="border-right: 1px solid rgba(0, 0, 0, 0.1)">전체</a></li>
                                       <!-- 선택된 카테고리의 경우 class 에 active 추가 -->
                                        <li><a id="L01M02S1" href="/ctgr/subcategory_product_list.do?i_sCategorycd1=L01&amp;i_sCategorynm1=기초&amp;i_sCategorycd2=L01M02&amp;i_sCategorynm2=클렌징&amp;i_sCategorycd3=L01M02S1&amp;i_sCategorynm3=클렌징폼" class="ctgr_box " style="border-right: 1px solid rgba(0, 0, 0, 0.1)">클렌징폼</a></li>
                                       <!-- 선택된 카테고리의 경우 class 에 active 추가 -->
                                        <li><a id="L01M02S2" href="/ctgr/subcategory_product_list.do?i_sCategorycd1=L01&amp;i_sCategorynm1=기초&amp;i_sCategorycd2=L01M02&amp;i_sCategorynm2=클렌징&amp;i_sCategorycd3=L01M02S2&amp;i_sCategorynm3=클렌징크림/로션/워터" class="ctgr_box " style="border-right: 1px solid rgba(0, 0, 0, 0.1)">클렌징크림/로션/워터</a></li>
                                      <!-- 선택된 카테고리의 경우 class 에 active 추가 -->
                                        <li><a id="L01M02S3" href="/ctgr/subcategory_product_list.do?i_sCategorycd1=L01&amp;i_sCategorynm1=기초&amp;i_sCategorycd2=L01M02&amp;i_sCategorynm2=클렌징&amp;i_sCategorycd3=L01M02S3&amp;i_sCategorynm3=클렌징젤/오일/티슈" class="ctgr_box " style="border-right: 1px solid rgba(0, 0, 0, 0.1)">클렌징젤/오일/티슈</a></li>
                                     <!-- 선택된 카테고리의 경우 class 에 active 추가 -->
                                        <li><a id="L01M02S4" href="/ctgr/subcategory_product_list.do?i_sCategorycd1=L01&amp;i_sCategorynm1=기초&amp;i_sCategorycd2=L01M02&amp;i_sCategorynm2=클렌징&amp;i_sCategorycd3=L01M02S4&amp;i_sCategorynm3=립&amp;아이 리무버" class="ctgr_box " >립&amp;아이 리무버</a></li>
                                </ul>
                            </div>
                        </div>
                </section>
	<section class="section-margin--small mb-5">
		<div class="container">
			<div class="row">
				<!-- 필터 -->
				<div class="col-xl-3 col-lg-4 col-md-5">
					<div class="sidebar-filter">
						<div class="top-filter-head">Product Filters</div>
						<div class="common-filter" style="padding-bottom: 15px">
							<div class="head">가격</div>
							<div class="price-range-area">
								<div id="price-range"></div>
								<div class="value-wrapper d-flex">
									<div class="price">가격:</div>
									<div id="lower-value"></div>
									<span>&#8361;</span>
									<!-- 원화표시 -->
									<div class="to">to</div>
									<div id="upper-value"></div>
									<span>&#8361;</span>
									<!-- 원화표시 -->
								</div>
							</div>
							<div class="custom-button"
								style="text-align: right; margin-top: 5px">
								<button onclick="onClickFilter()"
									style="background-color: #384aeb; color: white; border: none; margin-right: 15px;">검색</button>
							</div>
						</div>
					</div>
				</div>
				<!-- /필터 -->
				<div class="col-xl-9 col-lg-8 col-md-7">
					<!-- 목록 순서 -->
					<div class="filter-bar d-flex flex-wrap align-items-center">
						<div class="sorting">
							<select id="shit" onchange="proList(this)">
								<option value="1" data-sort-type="신상순">신상순</option>
								<option value="2" data-sort-type="인기순">인기순</option>
								<option value="3" data-sort-type="낮은가격순">낮은가격순</option>
								<!--  <option value="1">추천순</option> 찜개수 -->
							</select>
						</div>
						<span id=productCount></span>

					</div>
					<!-- 목록 순서 -->
					<!-- 상품 -->
					<section class="lattest-product-area pb-40 category-list">
						<div class="row" id="productListForm">
							<!-- JS: 각 상품 데이터를 받아와서 해당 상품에 대한 HTML코드를 생성해 반환 -->
						</div>
					</section>
					<!-- /상품 -->
				</div>
			</div>
		</div>
	</section>
	<!-- ================ /내용 ================= -->

	<common:footer />
	
	<script src="js/productList.js"></script>
</body>
</html>