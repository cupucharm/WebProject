/**
 * 
 */
window.onload = () => {

	let searchBtn = document.querySelector("#searchBtn");

	if (searchBtn != null) {
		searchBtn.onclick = () => {
			searchByUid();
		}
	}


	function searchByUid() {
		let searchInput = document.querySelector("#searchInput");

		window.location.href = '/webProjectSJ/Member/searchAdmin.do?searchInput=' + searchInput.value;
	}

}

