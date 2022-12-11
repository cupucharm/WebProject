/**
 * 
 */
window.onload = () => {
	let before_update_pwdBtn = document.querySelector("#before_update_pwdBtn");

	if (before_update_pwdBtn != null) {
		before_update_pwdBtn.onclick = () => {
			pwdUpdateCheck();
		}
	}

	async function pwdUpdateCheck() {

		let response = await fetch('/webProjectSJ/Member/pwdUpdateCheck?user_pwd=' + before_pwd.value + '&user_id=' + user_id.value);
		let jsonResult = await response.json();

		update_msg.innerHTML = jsonResult.message;

		if (jsonResult.status) {
			document.getElementById('user_name').readOnly = false;
			document.getElementById('user_phone').readOnly = false;
			document.getElementById('user_email').readOnly = false;
			document.getElementById('user_pwd').readOnly = false;
			document.getElementById('user_pwd').placeholder = user_pwd.value;


			document.getElementById('user_sex').type = "hidden";
			document.getElementById('user_sex_th').innerText = "";

			document.getElementById('user_birth').type = "hidden";
			document.getElementById('user_birth_th').innerText = "";

			document.getElementById('user_pwd').value = before_pwd.value;
			document.getElementById('user_pwd').type = "text";
		} else {
			document.getElementById('user_name').readOnly = true;
			document.getElementById('user_phone').readOnly = true;
			document.getElementById('user_email').readOnly = true;
			document.getElementById('user_pwd').readOnly = true;
			document.getElementById('user_pwd').placeholder = "*****";
		}


	}



	let updateMemberBtn = document.querySelector("#updateMemberBtn");
	if (updateMemberBtn != null) {
		updateMemberBtn.onclick = () => {
			updateMember();

		};
	}






	function updateMember() {


		document.getElementById("update_msg").style.display = "none";


		fetch('/webProjectSJ/Member/pwdUpdateCheck?user_pwd=' + before_pwd.value)
			.then(response => response.json())
			.then(jsonResult => {
				if (jsonResult.status == false) {
					alert(jsonResult.message);
				} else {

					fetch('/webProjectSJ/Member/updateMember?user_pwd=' + user_pwd.value + '&user_name=' + user_name.value
						+ '&user_phone=' + user_phone.value + '&user_email=' + user_email.value)
						.then(response => response.json())
						.then(jsonResult => {
							if (jsonResult.status == false) {
								alert(jsonResult.message);
							} else {
							
								document.getElementById('user_pwd').readOnly = true;
								document.getElementById('user_name').readOnly = true;
								document.getElementById('user_phone').readOnly = true;
								document.getElementById('user_email').readOnly = true;
								
								alert(jsonResult.message);	
								
								window.location.href = '/webProjectSJ/Member/MyPage';
							}
							location.reload();
						})

				}
			})
	}

}