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


	let tbody = document.querySelector("tbody");
	let tr = document.querySelector("tfoot tr");
	let insertFile = document.querySelector(".insertFile");

	function insertFileEventHandler() {
		let newTr = tr.cloneNode(true);
		tbody.append(newTr);
		newTr.style.display = "";

		newTr.querySelector(".insertFile").addEventListener("click", insertFileEventHandler);
		newTr.querySelector(".deleteFile").addEventListener("click", e => {
			tbody.removeChild(e.target.parentNode.parentNode)
		});
	}

	insertFile.addEventListener("click", insertFileEventHandler);



	if (updateBoardBtn != null) {
		updateBoardBtn.onclick = () => {
			updateBoard();
		}
	}
	
	
	function updateBoard() {
		//let bcontents = document.querySelector(".ck-content");

		fetch('/webProjectSJ/Board/updateBoard.do', {

			method: 'POST',
			cache: 'no-cache',
			body: new FormData(boardEdit)
		})

			.then(response => response.json())
			.then(jsonResult => {
				alert(jsonResult.message);
				if (jsonResult.status == true) {
					location.href = jsonResult.url;
				}
			});

	}
	
	
/*
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

		fetch('/webProjectSJ/Board/updateBoard.do', {
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
*/


}