document.addEventListener("DOMContentLoaded", async function (event) {
    disableLoading();
});

// disable loading
function disableLoading() {
    let loadingItem = document.getElementsByClassName("loading");
    if (loadingItem.length > 0) {
        loadingItem[0].style.display = "none";
    }
}
// enable loading
function enableLoading() {
    let loadingItem = document.getElementsByClassName("loading");
    if (loadingItem.length > 0) {
        loadingItem[0].style.display = "block";
    }
}

// Login 
const formLogin = document.getElementById('formLogin');
const emailLogin = document.getElementById('emailLogin');
const passWordLogin = document.getElementById('passWordLogin');

formLogin.addEventListener('submit', async function (event) {
    event.preventDefault();
    enableLoading();
    const url = 'http://localhost:50/StudentManagement/LoginServlet';
    const data = `emailLogin=${encodeURIComponent(emailLogin.value)}
    &passWordLogin=${encodeURIComponent(passWordLogin.value)}`;
    console.log(emailLogin.value);
    console.log(passWordLogin.value);
    try {
        const response = await fetch(url, {
            method: 'POST',
            headers: {
                'Content-Type': 'application/x-www-form-urlencoded'
            },
            body: data
        });
        if (!response.ok) {
            throw new Error('Login failed.');
        }
        let result = await response.json();
        document.getElementById('message').innerText = result.message;
        if (result.status === 200) {
            window.location.href = 'index.html';
        }

    } catch (error) {
        console.error('Error:', error);
        document.getElementById('message').innerText = "Login failed.";
    }
    disableLoading();
});

// Clear message
emailLogin.addEventListener('input', function () {
    document.getElementById('message').innerText = '';
})

passWordLogin.addEventListener('input', function () {
    document.getElementById('message').innerText = '';
})

// Register
const formRegister = document.getElementById('formRegister');
const emailRegister = document.getElementById('emailRegister');
const nameRegister = document.getElementById('nameRegister');
const passWordRegister = document.getElementById('passWordRegister');
const confirmPassWordRegister = document.getElementById('confirmPassWordRegister');
const emailPattern = /^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\.[a-zA-Z]{2,}$/;
const passwordPattern = /^(?=.*[A-Za-z])(?=.*\d)(?=.*[\W_])[A-Za-z\d\W_]{8,}$/;
const namePattern = /^[A-Za-zÀ-Ỹà-ỹ\s]{2,50}$/;

formRegister.addEventListener('submit', async function (event) {
    event.preventDefault();
    document.getElementById('errorEmail').innerText = '';
    document.getElementById('errorName').innerText = '';
    document.getElementById('errorPassword').innerText = '';
    document.getElementById('errorPasswordConfirm').innerText = '';
    document.getElementById('messageRegister').innerText = '';
    let countError = 0;
    // Validate email
    if (!emailPattern.test(emailRegister.value)) {
        countError++;
        document.getElementById('errorEmail').innerText = "Invalid email.";
    }

    // Validate Name
    if (!namePattern.test(nameRegister.value)) {
        countError++;
        document.getElementById('errorName').innerText = "Name can only contain letters and spaces.";
    }

    // Validate password
    if (!passwordPattern.test(passWordRegister.value)) {
        countError++;
        document.getElementById('errorPassword').innerText = "Password must be at least 8 characters, including (1-9, a-z, A-Z,!@#$%...)";
    } else {
        // Validate password confirm 
        if (passWordRegister.value != confirmPassWordRegister.value) {
            countError++;
            document.getElementById('errorPasswordConfirm').innerText = "Confirm password does not match.";
        }
    }
    if (countError === 0) {
        enableLoading();
        const url = 'http://localhost:50/StudentManagement/RegistrationServlet';
        const data = `emailRegister=${encodeURIComponent(emailRegister.value.trim())}
        &nameRegister=${encodeURIComponent(nameRegister.value.trim())}
        &passWordRegister=${encodeURIComponent(passWordRegister.value.trim())}`;
        try {
            const response = await fetch(url, {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/x-www-form-urlencoded'
                },
                body: data
            });
            if (!response.ok) {
                throw new Error('Account registration failed.');
            }
            let result = await response.json();
            document.getElementById('messageRegister').innerText = result.message;
            if (result.status === 200) {
                window.location.href = 'login_registration.html';
            }

        } catch (error) {
            console.error('Error:', error);
            document.getElementById('message').innerText = "Account registration failed.";
        }
        disableLoading();
    }
});

// Clear error message
emailRegister.addEventListener('input', function () {
    document.getElementById('errorEmail').innerText = '';
})

nameRegister.addEventListener('input', function () {
    document.getElementById('errorName').innerText = '';
})


passWordRegister.addEventListener('input', function () {
    document.getElementById('errorPassword').innerText = '';
})

confirmPassWordRegister.addEventListener('input', function () {
    document.getElementById('errorPasswordConfirm').innerText = '';
})

// Clear form after switch login and signup
const switchToSignup = document.querySelector('#checkSignup');
const switchToLogin = document.querySelector('#checkLogin');

switchToSignup.addEventListener('click', function(){
    document.getElementById('formLogin').reset();
    document.getElementById('message').innerText = '';
});

switchToLogin.addEventListener('click', function(){
    document.getElementById('formRegister').reset();
    document.getElementById('errorEmail').innerText = '';
    document.getElementById('errorName').innerText = '';
    document.getElementById('errorPassword').innerText = '';
    document.getElementById('errorPasswordConfirm').innerText = '';
    document.getElementById('messageRegister').innerText = '';
});