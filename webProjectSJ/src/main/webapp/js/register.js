/**
 * 
 */
window.onload = () => {
	
	let user_id = document.querySelector("#user_id");

	if (user_id != null) {
		user_id.onblur = () => {
			dupUidCheck();
		}
	}
	
	async function dupUidCheck() {
		let response = await fetch('/webProjectSJ/Member/dupUidCheck.do?user_id=' + user_id.value);
		let jsonResult = await response.json();

		uid_valid_msg.innerHTML = jsonResult.message;
	}
	
	
	let pwdConfirm = document.querySelector("#pwdConfirm");
	let user_pwd = document.querySelector("#user_pwd");

	if (pwdConfirm != null) {
		pwdConfirm.onblur = () => {
			pwdCheck();
		}
	}

	async function pwdCheck() {
		let response = await fetch('/webProjectSJ/Member/pwdCheck.do?user_pwd=' + user_pwd.value+'&pwdConfirm=' +pwdConfirm.value);
		let jsonResult = await response.json();

		pwd_valid_msg.innerHTML = jsonResult.message;
	}
	
	let registerBtn = document.querySelector("#registerBtn");
	if (registerBtn != null) {
		registerBtn.onclick = () => {
			register();
		}
	}
	
	function register() {
		let sex= document.querySelector('input[name="user_sex"]:checked').value;

		let param = {
			"user_id": user_id.value,
			"user_name": user_name.value,
			"user_pwd": user_pwd.value,
			"pwdConfirm": pwdConfirm.value,
			"user_phone": user_phone.value,
			"user_email": user_email.value,
			"user_sex": sex,
			"user_birth": user_birth.value
		};

		fetch('/webProjectSJ/Member/register.do', {
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
