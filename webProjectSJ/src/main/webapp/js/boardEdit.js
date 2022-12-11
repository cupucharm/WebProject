/**
 * 
 */
window.onload = () => {
	let updateBoardBtn = document.querySelector("#updateBoardBtn");
	let btitle = document.querySelector("#title");
	let realBno = document.querySelector("#realBno");
	let num = document.querySelector("#num");
	let page = document.querySelector("#page");

	ClassicEditor
		.create(document.querySelector('#bcontents'), { language: "ko" })
		.catch(error => {
			console.error(error);
		});



	if (updateBoardBtn != null) {
		updateBoardBtn.onclick = () => {
			updateBoard();
		}
	}

	function updateBoard() {
		let bcontents = document.querySelector(".ck-content");

		let param = {
			"realBno": realBno.value,
			"btitle": btitle.value,
			"bcontents": bcontents.innerHTML,
			"num": num.value,
			"page": page.value
		};
		console.log(param);
		console.log(JSON.stringify(param));

		fetch('/webProjectSJ/Board/updateBoard', {
			//option
			method: 'POST',
			headers: {
				'Content-Type': 'application/json;charset=utf-8'
			},
			body: JSON.stringify(param)
		})
			.then(response => response.json())
			.then(jsonResult => {
				alert(jsonResult.message);
				if (jsonResult.status == true) {
					location.href = jsonResult.url;
				}
			});

	}



}