/**
 * 
 */
window.onload = () => {

	let searchBtn = document.querySelector("#searchBtn");

	if (searchBtn != null) {
		searchBtn.onclick = () => {
			boardSearch();
		}
	}
	
	 function boardSearch() {
		let select = document.getElementById("searchSelect");
		let selectValue = select.options.selectedIndex;
		let searchCondition = select.options[selectValue].value;
		let searchInput = document.querySelector("#searchInput");

		
		window.location.href = '/webProjectSJ/Board/search?searchCondition=' + searchCondition +'&searchContent='+searchInput.value+ '&pageNum=1';
	}
	
	
	let all = document.querySelector("#all");

	if (all != null) {
		all.onclick = () => {
			boardViewAll();
		}
	}
	
	 function boardViewAll() {
		window.location.href = '/webProjectSJ/Board/view?content=' + all.value + '&pageNum=1';
	}
	
	let notice = document.querySelector("#notice");

	if (notice != null) {
		notice.onclick = () => {
			boardViewNotice();
		}
	}
	
	 function boardViewNotice() {
		window.location.href = '/webProjectSJ/Board/view?content=' + notice.value + '&pageNum=1';
	}
	
	let commom = document.querySelector("#commom");

	if (commom != null) {
		commom.onclick = () => {
			boardViewCommon();
		}
	}
	
	 function boardViewCommon() {
		window.location.href = '/webProjectSJ/Board/view?content=' + commom.value + '&pageNum=1';
	}
	
	let Question = document.querySelector("#Question");

	if (Question != null) {
		Question.onclick = () => {
			boardViewQuestion();
		}
	}
	
	 function boardViewQuestion() {
		window.location.href = '/webProjectSJ/Board/view?content=' + Question.value + '&pageNum=1';
	}
	
	}