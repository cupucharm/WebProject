/**
 * 
 */
window.onload = () => {
	let user_id = document.querySelector("#user_id");
	let user_name = document.querySelector("#user_name");
	let user_phone = document.querySelector("#user_phone");
	

	let searchIdBtn = document.querySelector("#searchIdBtn");
	if (searchIdBtn != null) {
		searchIdBtn.onclick = () => {
			jsSearchId();
		};
	}
	
	let searchPwBtn = document.querySelector("#searchPwBtn");
	if (searchPwBtn != null) {
		searchPwBtn.onclick = () => {
			jsSearchPw();
		};
	}
	
	async function jsSearchId() {
		let response = await fetch('/webProjectSJ/Member/searchId?user_name=' + user_name.value + "&user_phone=" + user_phone.value);
		let jsonResult = await response.json();

		let checkId = document.querySelector("#checkId");
		checkId.innerHTML = jsonResult.message;
	}

	async function jsSearchPw() {
		let response = await fetch('/webProjectSJ/Member/searchPw?user_id=' + user_id.value + '&user_name=' + user_name.value + "&user_phone=" + user_phone.value);
		let jsonResult = await response.json();

		let checkPw = document.querySelector("#checkPw");
		checkPw.innerHTML = jsonResult.message;
	}
}