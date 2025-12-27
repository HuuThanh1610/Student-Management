//-----------------Main-Student-Management---------------------------------//
document.addEventListener("DOMContentLoaded", async function (event) {
    // document ready

    // select Option Class
    selectOptionClass();
    // Disable loading
    disableLoading();
    await loadingItems(1);// curent page 1
});

// event for dopdownlist Class
function selectOptionClass() {
    let select = document.getElementById("className");
    select.onchange = function () {
        let value = select.value;
        if (value != "") {
            select.classList.remove("none-value");
        }
        else {
            select.classList.add("none-value");
        }

    };
    select.onfocus = function () {
        // console.log("onfocus");
    };
    select.onblur = function () {
        // console.log("onblur");
    };
}

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

// event active row in table 
function studentActive() {
    const select = document.getElementById("table-student-infomation");
    const selectTr = select.getElementsByTagName("tr");
    if (selectTr.length > 0) {
        let i = 1;
        while (i <= selectTr.length) {
            let selectChild = document.getElementById("tr-" + i);
            if (selectChild) {
                selectChild.addEventListener('click', function (event) {

                    if (selectTr.length > 0) {
                        let ii = 1;
                        while (ii <= selectTr.length) {
                            let selectChildM = document.getElementById("tr-" + ii);
                            if (selectChildM) {
                                selectChildM[ii].classList.remove("active");
                            }
                            ii++;
                        }
                    }

                    // Remove previous highlight class

                    // add highlight to the parent tr of the clicked td
                    event.classList.add("active");
                });
            }
            i++;
        }

    }

    console.log('active');
}


// ----------------Validate input value into form----------------//

// Lấy phần tử form
const emailPattern = /^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\.[a-zA-Z]{2,}$/;
const form = document.getElementById('formcontainer');
const studentName = document.getElementById('studentName');
const identification = document.getElementById('identification');
const genderStudent = document.getElementById('genderStudent');
const className = document.getElementById('className');
const addressStudent = document.getElementById('addressStudent');
const dateOfBirthStudent = document.getElementById('dateOfBirthStudent');
const phoneNumber = document.getElementById('phoneNumber');
const email = document.getElementById('email');
const studentID = document.getElementById('studentID');
const schoolYear = document.getElementById('schoolYear');
const reason = document.getElementById('reason');
// ------------------------------------------------------------------------------//
// Bắt sự kiện khi submit form 
form.addEventListener('submit', async function (event) {
    // Ngăn không cho form tự động gửi đi
    event.preventDefault();

    // Get data-mode
    const mode = form.getAttribute("data-mode");

    if (mode === "create") {
         // Validate student name
         if (!validateStudentName(studentName.value)) {
            alert("Invalid Student Name.");
            email.style.color = 'red';
            return;
        }

        // Validate identification  
        if (!validateIdentification(identification)) {
            alert("Invalid Identification.");
            identification.style.color = 'red';
            return;
        }

        // Validate email
        if (!emailPattern.test(email.value)) {
            alert("Invalid email.");
            email.style.color = 'red';
            return;
        }

        // Validate gender
        if (genderStudent.value == '') {
            alert("Please choose gender.");
            return;
        }

        // Validate Class
        if (className.value == '') {
            alert("Please choose class.");
            return;
        }

        // Validate Birtday
        if (!validateBirthday(dateOfBirthStudent)) {
            alert('Invalid birthday.');
            return;
        }

        // Validate Phone
        if (!validatePhone(phoneNumber)) {
            alert('Invalid phone number.');
            return;
        }
        //Notification
        alert('Submit success.');
        let createAPIRespone = new Object(await createStudent(studentName.value, identification.value, email.value, genderStudent.value, className.value, schoolYear.value, dateOfBirthStudent.value, addressStudent.value, phoneNumber.value));
        if (createAPIRespone != null) {
            console.log(createAPIRespone.data);
            alert(createAPIRespone.message);
            if (createAPIRespone.status === 200) {
                // window.location.reload();
                loadingItems(1);
                resetFormSubmitbtn();
                closeFormCreate();
            }
        }
    } else if (mode === "update") {
        if (!studentID.value) {
            alert("Student ID is required for update.");
            return;
        }
        console.log();
        let updateAPIRespone = new Object(await updateStudent(studentID.value, studentName.value, identification.value, email.value, genderStudent.value, className.value, schoolYear.value, dateOfBirthStudent.value, addressStudent.value, phoneNumber.value));
        if (updateAPIRespone != null) {
            console.log(updateAPIRespone.data);
            alert(updateAPIRespone.message);
            if (updateAPIRespone.status === 200) {
                // window.location.reload();
                loadingItems(1);
                closeFormCreate();
            }
        }
    }

});

//validate full name
function validateStudentName(studentName) {
    const nameRegex = /^[A-Z][a-z]+( [A-Z][a-z]+)+$/;
    return nameRegex.test(studentName.value.trim());
}

//validate Identification
function validateIdentification(identification) {
    if (identification.value.length != 12) {
        return false;
    }
    const regex = /^\d{12}$/;
    if (!regex.test(identification.value)) {
        return false;
    }
    return true;
}

//validate email
function validateEmail(email) {
    return emailPattern.test(email.value); // Trả về true hoặc false
}

//validate Birthday
function validateBirthday(dateOfBirthStudent) {
    const today = new Date();
    const currentDay = today.getDate();
    const currentMonth = today.getMonth(); // Tháng trong JS bắt đầu từ 0
    const currentYear = today.getFullYear();
    const currentDate = new Date(currentYear, currentMonth, currentDay);
    const [year, month, day] = dateOfBirthStudent.value.split('-').map(Number);
    const birthDate = new Date(year, month - 1, day);

    if (birthDate >= currentDate) {
        return false;
    }

    if (year < 1900 || year > currentYear) {
        return false;
    }

    if (month < 1 || month > 12) {
        return false;
    }

    if (month == 1 || month == 3 || month == 5 || month == 7 || month == 8 || month == 10 || month == 12) {
        if (day < 1 || day > 31) {
            return false;
        }
    } else if (month == 4 || month == 6 || month == 9 || month == 11) {
        if (day < 1 || day > 30) {
            return false;
        }
    } else if (month == 2) {
        if ((year % 400 == 0 || (year % 4 == 0 && year % 100 != 0))) {
            if (day < 1 || day > 29) {
                return false;
            }
        } else {
            if (day < 1 || day > 28) {
                return false;
            }
        }
    }


    return true;
}

//validate email
function validatePhone(phoneNumber) {
    const phonePattern = /^0[0-9]{9}$/;
    return phonePattern.test(phoneNumber.value); // Trả về true hoặc false
}

//Reset form when submit
function resetFormSubmitbtn() {
    document.getElementById("formcontainer").reset();
}

// Change text color when user change current text
studentName.addEventListener('change', function (event) {
    studentName.style.color = 'red';
});

email.addEventListener('change', function (event) {
    email.style.color = 'red';
});

identification.addEventListener('change', function (event) {
    identification.style.color = 'red';

});

addressStudent.addEventListener('change', function (event) {
    addressStudent.style.color = 'red';
});

dateOfBirthStudent.addEventListener('change', function (event) {
    dateOfBirthStudent.style.color = 'red';
});

genderStudent.addEventListener('change', function (event) {
    genderStudent.style.color = 'red';
});

className.addEventListener('change', function (event) {
    className.style.color = 'red';
});
// Change text color when user change current text

//------------------------------------------------------------------------------//






//----------------------------Validate input value into search box----------------------------//
// Lấy phần tử từ search box
const searchStudentName = document.getElementById('searchStudentName');
const searchIdentification = document.getElementById('searchIdentification');
const searchClassName = document.getElementById('searchClassName');
const btnSearch = document.getElementById('btnSearch');
let inputSearchStudentName = document.getElementById("searchStudentName");

// ---------------------------------------------------------------------------------------------------------------------------//

// Execute a function when the user presses a key on the keyboard
inputSearchStudentName.addEventListener("keypress", function (event) {
    // If the user presses the "Enter" key on the keyboard
    if (event.key === "Enter") {
        // Cancel the default action, if needed
        event.preventDefault();
        // Trigger the button element with a click
        btnSearch.click();
    }
});

// ---------------------------------------------------------------------------------------------------------------------------//


// Bắt sự kiện click on button search
btnSearch.onclick = async function () {
    enableLoading();
    let searchAPIRespone = new Object(await fetchStudents(searchStudentName.value, searchIdentification.value, searchClassName.value));
    if (searchAPIRespone != null) {
        appendStudent(searchAPIRespone.data, 1);
    }
    disableLoading();
}

// Validate text box searchStudentName
function validateSearchName(searchStudentName) {
    if (searchStudentName.value.length < 3 || searchStudentName.value.length > 20) {
        return false;
    }
    return true;
}

// Validate text box searchIdentification
function validateSearchIdentification(searchIdentification) {
    if (searchIdentification.value.length != 12) {
        return false;
    }
    const regex = /^\d{12}$/;
    if (!regex.test(searchIdentification.value)) {
        return false;
    }
    return true;
}
//------------------------------------------API Student Managerment----------------------------------------------//


//function send request create student
async function createStudent(studentName, identificationStudent, email, genderStudent, className, schoolYear, DateOfBirthStudent, address, phoneNumber) {
    const url = 'http://localhost:50/StudentManagement/CreateStudent';

    const data = `studentName=${encodeURIComponent(studentName)}
    &genderStudent=${encodeURIComponent(genderStudent)}
    &identificationStudent=${encodeURIComponent(identificationStudent)}
    &email=${encodeURIComponent(email)}
    &className=${encodeURIComponent(className)}
    &schoolYear=${encodeURIComponent(schoolYear)}
    &address=${encodeURIComponent(address)}
    &DateOfBirthStudent=${encodeURIComponent(DateOfBirthStudent)}
    &phoneNumber=${encodeURIComponent(phoneNumber)}`;

    try {
        const response = await fetch(url, {
            method: 'POST',
            headers: {
                'Content-Type': 'application/x-www-form-urlencoded'
            },
            body: data
        });

        if (!response.ok) {
            throw new Error('Failed to create student');
        }
        return await response.json();

    } catch (error) {
        console.error('Error:', error);
    }
}

//function send request update student
async function updateStudent(studentID, studentName, identificationStudent, email, genderStudent, className, schoolYear, DateOfBirthStudent, address, phoneNumber) {
    const url = 'http://localhost:50/StudentManagement/UpdateStudent';

    const data = `studentID=${encodeURIComponent(studentID)}
    &studentName=${encodeURIComponent(studentName)}
    &genderStudent=${encodeURIComponent(genderStudent)}
    &identificationStudent=${encodeURIComponent(identificationStudent)}
    &email=${encodeURIComponent(email)}
    &className=${encodeURIComponent(className)}
    &schoolYear=${encodeURIComponent(schoolYear)}
    &address=${encodeURIComponent(address)}
    &DateOfBirthStudent=${encodeURIComponent(DateOfBirthStudent)}
    &phoneNumber=${encodeURIComponent(phoneNumber)}`;


    try {
        const response = await fetch(url, {
            method: 'POST',
            headers: {
                'Content-Type': 'application/x-www-form-urlencoded'
            },
            body: data
        });

        if (!response.ok) {
            throw new Error('Failed to update student');
        }
        return await response.json();

    } catch (error) {
        console.error('Error:' + error);
    }
}



//function fetch Data - search student
async function fetchStudents(name, iden, nameClass) {
    try {
        const url = `http://localhost:50/StudentManagement/SearchStudent?studentName=${encodeURIComponent(name)}&studentIden=${encodeURIComponent(iden)}&className=${encodeURIComponent(nameClass)}`;

        // Gửi request đến Servlet
        const response = await fetch(url, {
            method: 'GET'
        });

        if (!response.ok) {
            throw new Error('Failed to fetch data from server !')
        }
        console.log(url);
        return await response.json();


    } catch (error) {
        console.error('Error:', error);
        alert('Error fetching student data.');
        return [];
    }
}

//fetch student by studentID
async function fetchStudentByID(studentID) {
    try {
        const url = `http://localhost:50/StudentManagement/GetByStudentID?studentID=${encodeURIComponent(studentID)}`;

        // Gửi request đến Servlet
        const response = await fetch(url, {
            method: 'GET'
        });

        if (!response.ok) {
            throw new Error('Failed to fetch data from server !')
        }
        console.log(url);
        return await response.json();


    } catch (error) {
        console.error('Error:', error);
        alert('Error fetching student data.');
    }
}

//function send request delete student
async function deleteStudent(studentID, reason) {
    const url = 'http://localhost:50/StudentManagement/DeleteStudent';

    const data = `studentID=${encodeURIComponent(studentID)}&reason=${encodeURIComponent(reason)}`;


    try {
        const response = await fetch(url, {
            method: 'POST',
            headers: {
                'Content-Type': 'application/x-www-form-urlencoded'
            },
            body: data
        });

        if (!response.ok) {
            throw new Error('Failed to delete student');
        }
        return await response.json();

    } catch (error) {
        console.error('Error:', error);
    }
}
//function fetch data for drop down list
async function fetchDataDropdown() {
    const url = 'http://localhost:50/StudentManagement/DropDownListData';
    try {
        const response = await fetch(url, {
            method: 'GET'
        });

        if (!response.ok) {
            throw new Error('Failed to fetch data for drop down list.');
        }
        return await response.json();

    } catch (error) {
        console.error('Error:', error);
    }
} 


//----------------------------------------------------------------------------------------//
document.addEventListener("DOMContentLoaded", async function () {
    const defaultOption = document.createElement("option");
    defaultOption.value = "";
    defaultOption.selected = true; 
    defaultOption.textContent = "-- Choose school year --";
    schoolYear.prepend(defaultOption);

    const defaultOption1 = document.createElement("option");
    defaultOption1.value = "";
    defaultOption1.selected = true; 
    defaultOption1.textContent = "-- Choose class name --";
    className.prepend(defaultOption1);

    const defaultOption3 = document.createElement("option");
    defaultOption3.value = "";
    defaultOption3.selected = true; 
    defaultOption3.textContent = "-- Choose class name --";
    searchClassName.prepend(defaultOption3);

    let fetchAPIRespone = await fetchDataDropdown();
    if (fetchAPIRespone != null) {
        console.log(fetchAPIRespone.data);
        if (fetchAPIRespone.status === 200) {
            fetchAPIRespone.data.className.forEach(classItem  => {
                const option = document.createElement('option');
                option.value = classItem;
                option.textContent = classItem;
                className.appendChild(option);
            });
            fetchAPIRespone.data.className.forEach(classItem  => {
                const option = document.createElement('option');
                option.value = classItem;
                option.textContent = classItem;
                searchClassName.appendChild(option);
            });
            fetchAPIRespone.data.schoolYear.forEach(year  => {
                const option = document.createElement('option');
                option.value = year;
                option.textContent = year;
                schoolYear.appendChild(option);
            });
        }
    }
});
