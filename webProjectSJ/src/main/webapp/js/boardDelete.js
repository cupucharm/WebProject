/**
 * 
 */
window.onload = () => {

	let deleteBoardBtn = document.querySelector("#deleteBoardBtn");

	deleteBoardBtn.addEventListener("click", async e => {
		const bno = e.target.dataset.bno;
		const page = e.target.dataset.page;
		const response = await fetch(`/webProjectSJ/Board/deleteBoard?bno=${bno}&page=${page}`, {

		});
		const jsonResult = await response.json();

		alert(jsonResult.message);
		location.href = jsonResult.url;
	});
}