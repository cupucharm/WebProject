/**
 * 
 */
window.onload = () => {

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


	let replyBtn = document.querySelector("#replyBtn");
	if (replyBtn != null) {
		replyBtn.onclick = e => {
			reply(e);
		}
	}

	function reply(e) {
		e.preventDefault();	
		bcontents.value = document.querySelector(".ck-content").innerHTML;
		fetch('/webProjectSJ/Board/reply.do', {

			method: 'POST',
			cache: 'no-cache',
			body: new FormData(boardUpload)
		})

			.then(response => response.json())
			.then(jsonResult => {
				alert(jsonResult.message);
				if (jsonResult.status == true) {
					location.href = jsonResult.url;
				}
			});
	}

	let uploadBtn = document.querySelector("#uploadBtn");
	if (uploadBtn != null) {
		uploadBtn.onclick = e => {
			upload(e);
		}
	}

	function upload(e) {
		e.preventDefault();	
		bcontents.value = document.querySelector(".ck-content").innerHTML;
		fetch('/webProjectSJ/Board/boardUpload.do', {

			method: 'POST',
			cache: 'no-cache',
			body: new FormData(boardUpload)
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