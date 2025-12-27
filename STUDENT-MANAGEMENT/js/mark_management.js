//-----------------Main-Student-Management---------------------------------//
document.addEventListener("DOMContentLoaded", function (event) {
    // document ready

    // select Option Class
    selectOptionClass();
    // Disable loading
    disableLoading();
    loadingItems(1);// curent page 1
});

// event for dopdownlist Class
function selectOptionClass() {
    let select = document.getElementById("searchClassName");
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
        console.log("onfocus");
    };
    select.onblur = function () {
        console.log("onblur");
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

//------------------------------------------Find Student to create mark----------------------------------------------//
// Lấy phần tử từ search box
// const searchStudentNameToCreateMark = document.getElementById('studentNameSearchForCreate');
const searchIdentificationToCreateMark = document.getElementById('identificationSearchForCreate');
const btnSearchFormCreate = document.getElementById('btnSearchFormCreate');
const searchInFormCreate = document.getElementById('searchInFormCreate');
let studentCreateMark;

btnSearchFormCreate.onclick = async function () {
    // Xóa tất cả các thẻ <p> cũ
    const existingParagraphs = searchInFormCreate.querySelectorAll('p');
    existingParagraphs.forEach(p => p.remove());

    // Validate identification  
    if (!validateIdentification(searchIdentificationToCreateMark)) {
        const p = document.createElement('p');
        p.textContent = `Identification invalid. Please enter again !`;
        p.style.color = 'red';
        searchInFormCreate.appendChild(p);
        return;
    }

    enableLoading();
    let searchAPIRespone = new Object(await fetchStudents('', searchIdentificationToCreateMark.value, ''));
    if (searchAPIRespone != null && searchAPIRespone.status == 200) {
        studentCreateMark = searchAPIRespone.data[0];
    }
    disableLoading();

    if (studentCreateMark == null) {
        const p = document.createElement('p');
        p.textContent = `Student dose not exist, so can not create new mark !`;
        p.style.color = 'red';
        searchInFormCreate.appendChild(p);
        return;
    } else {
        const p = document.createElement('p');
        p.textContent = `Student: ${studentCreateMark.name}, Identification: ${studentCreateMark.identification}`;
        p.style.color = '#1a7431';
        searchInFormCreate.appendChild(p);
    }
}

// ----------------Validate input value into form----------------//
// Lấy phần tử form
const formPopupCreate = document.getElementById('formPopupCreate');
const markIDForm = document.getElementById('markID');
const studentName = document.getElementById('studentName');
const subject = document.getElementById('subject');
const mark = document.getElementById('mark');
const semester = document.getElementById('semester');
const reason = document.getElementById('reason');

// ------------------------------------------------------------------------------//
// Bắt sự kiện khi submit form 
formPopupCreate.addEventListener('submit', async function (event) {
    // Ngăn không cho form tự động gửi đi
    event.preventDefault();

    // Get data-mode
    const mode = formPopupCreate.getAttribute("data-mode");

    if (mode === "create") {

        if (studentCreateMark == null) {
            alert("Please find student to create mark.");
            return;
        }

        // Validate semester
        if (semester.value == '') {
            alert("Please choose semester.");
            return;
        }

        // Validate subject
        if (subject.value == '') {
            alert("Please choose subject.");
            return;
        }

        //validate mark
        if (mark.value < 0 || mark.value > 10) {
            alert("Invalid mark.");
            return;
        }

        alert('Submit success.');
        console.log(studentCreateMark.studentID);
        let createAPIRespone = new Object(await createGrade(subject.value, studentCreateMark.studentID, mark.value, semester.value));
        if (createAPIRespone != null) {
            console.log(createAPIRespone.data);
            alert(createAPIRespone.message);
            if (createAPIRespone.status === 200) {
                loadingItems(1);
                resetFormSubmitbtn();
                closeFormCreate();
            }
        }
    } else if (mode === "update") {
        if (!markIDForm.value) {
            alert("Mark ID is required for update.");
            return;
        }

        //validate mark
        if (mark.value < 0 || mark.value > 10) {
            alert("Invalid mark.");
            return;
        }

        let updateAPIRespone = new Object(await updateGrade(markIDForm.value, mark.value, reason.value));
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




//Reset form when submit
function resetFormSubmitbtn() {
    document.getElementById("formcontainer").reset();
}

// // Change text color when user change current text
// studentName.addEventListener('change', function (event) {
//     studentName.style.color = 'red';
// });

// identification.addEventListener('change', function (event) {
//     identification.style.color = 'red';

// });

subject.addEventListener('change', function (event) {
    subject.style.color = 'red';
});

// className.addEventListener('change', function (event) {
//     className.style.color = 'red';
// });

mark.addEventListener('change', function (event) {
    mark.style.color = 'black';
});

semester.addEventListener('change', function (event) {
    semester.style.color = 'black';
});

// schoolYear.addEventListener('change', function (event) {
//     schoolYear.style.color = 'black';
// });
// Change text color when user change current text
//------------------------------------------------------------------------------//

//-------------------------insert information student to popup delete--------------------//



//-----------------------------------------------------------------------------------------//



//----------------------------Validate input value into search box----------------------------//
// Lấy phần tử từ search box
const searchStudentName = document.getElementById('searchStudentName');
const searchClassName = document.getElementById('searchClassName');
const searchSubject = document.getElementById('searchSubject');
const searchSemester = document.getElementById('searchSemester');
const searchSchoolYear = document.getElementById('searchSchoolYear');
const btnSearch = document.getElementById('btnSearch');

// Bắt sự kiện click on button search
btnSearch.onclick = async function () {
    enableLoading();
    let searchAPIRespone = new Object(await fetchGrade(searchStudentName.value.trim(), searchClassName.value.trim(), searchSubject.value.trim(), searchSemester.value.trim(), searchSchoolYear.value.trim()));
    if (searchAPIRespone != null) {
        appendGrade(searchAPIRespone.data, 1);
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

//----------------------------------------------------------------------------------------//


//------------------------------------------API Grade Managerment----------------------------------------------//


//function send request create Grade
async function createGrade(subjectID, studentID, markOfSubject, semester) {
    const url = 'http://localhost:50/StudentManagement/CreateGrade';

    const data = `subjectID=${encodeURIComponent(subjectID)}
    &studentID=${encodeURIComponent(studentID)}
    &markOfSubject=${encodeURIComponent(markOfSubject)}
    &semester=${encodeURIComponent(semester)}`;

    try {
        const response = await fetch(url, {
            method: 'POST',
            headers: {
                'Content-Type': 'application/x-www-form-urlencoded'
            },
            body: data
        });

        if (!response.ok) {
            throw new Error('Failed to create grade');
        }
        return await response.json();

    } catch (error) {
        console.error('Error:', error);
    }
}

//function send request update student
async function updateGrade(markID, markOfSubject, reason) {
    const url = 'http://localhost:50/StudentManagement/UpdateGrade';

    const data = `markID=${encodeURIComponent(markID)}
    &markOfSubject=${encodeURIComponent(markOfSubject)}
    &reason=${encodeURIComponent(reason)}`;

    try {
        const response = await fetch(url, {
            method: 'POST',
            headers: {
                'Content-Type': 'application/x-www-form-urlencoded'
            },
            body: data
        });

        if (!response.ok) {
            throw new Error('Failed to update grade');
        }
        return await response.json();

    } catch (error) {
        console.error('Error:', error);
    }
}



//function fetch Data - search grade
async function fetchGrade(name, className, subject, semester, schoolYear) {
    try {
        const url = `http://localhost:50/StudentManagement/SearchGrade?studentName=${encodeURIComponent(name)}&className=${encodeURIComponent(className)}&subjectID=${encodeURIComponent(subject)}&semester=${encodeURIComponent(semester)}&schoolYear=${encodeURIComponent(schoolYear)}`;

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
        alert('Error fetching grade data.');
        return [];
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

//fetch subject information by subjectID
async function fetchSubjectBySubjectID(subjectID) {
    try {
        const url = `http://localhost:50/StudentManagement/GetSubjectBySubjectID?subjectID=${encodeURIComponent(subjectID)}`;

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
        alert('Error fetching subject data.');
    }
}

//fetch class information by StudentID
async function fetchClassByStudentID(studentID) {
    try {
        const url = `http://localhost:50/StudentManagement/GetClassByStudentID?studentID=${encodeURIComponent(studentID)}`;

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
        alert('Error fetching class data.');
    }
}


//fetch grade information by markID
async function fetchGradeByMarkID(markID) {
    try {
        const url = `http://localhost:50/StudentManagement/GetByGradeID?markID=${encodeURIComponent(markID)}`;

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
        alert('Error fetching grade data.');
    }
}



//function send request delete grade
async function deleteGrade(markID) {
    const url = 'http://localhost:50/StudentManagement/DeleteGrade';

    const data = `markID=${encodeURIComponent(markID)}`;


    try {
        const response = await fetch(url, {
            method: 'POST',
            headers: {
                'Content-Type': 'application/x-www-form-urlencoded'
            },
            body: data
        });

        if (!response.ok) {
            throw new Error('Failed to delete grade');
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
    defaultOption.textContent = "-- Choose subject --";
    searchSubject.prepend(defaultOption);

    const defaultOption1 = document.createElement("option");
    defaultOption1.value = "";
    defaultOption1.selected = true; 
    defaultOption1.textContent = "-- Choose school year --";
    searchSchoolYear.prepend(defaultOption1);

    const defaultOption3 = document.createElement("option");
    defaultOption3.value = "";
    defaultOption3.selected = true; 
    defaultOption3.textContent = "-- Choose class name --";
    searchClassName.prepend(defaultOption3);

    const defaultOption4 = document.createElement("option");
    defaultOption4.value = "";
    defaultOption4.selected = true; 
    defaultOption4.textContent = "-- Choose semester --";
    searchSemester.prepend(defaultOption4);

    const defaultOption5 = document.createElement("option");
    defaultOption5.value = "";
    defaultOption5.selected = true; 
    defaultOption5.textContent = "-- Choose semester --";
    semester.prepend(defaultOption5);

    const defaultOption6 = document.createElement("option");
    defaultOption6.value = "";
    defaultOption6.selected = true; 
    defaultOption6.textContent = "-- Choose subject --";
    subject.prepend(defaultOption6);

    let fetchAPIRespone = await fetchDataDropdown();
    if (fetchAPIRespone != null) {
        console.log(fetchAPIRespone.data);
        if (fetchAPIRespone.status === 200) {
            fetchAPIRespone.data.subject.forEach(subjectItem  => {
                const option = document.createElement('option');
                option.value = subjectItem.subjectID;
                option.textContent = subjectItem.subjectName;
                subject.appendChild(option);
            });

            fetchAPIRespone.data.subject.forEach(subjectItem  => {
                const option = document.createElement('option');
                option.value = subjectItem.subjectID;
                option.textContent = subjectItem.subjectName;
                searchSubject.appendChild(option);
            });

            fetchAPIRespone.data.className.forEach(classItem  => {
                const option = document.createElement('option');
                option.value = classItem;
                option.textContent = classItem;
                searchClassName.appendChild(option);
            });

            fetchAPIRespone.data.semester.forEach(semesterItem  => {
                const option = document.createElement('option');
                option.value = semesterItem.semesterID;
                option.textContent = semesterItem.semesterName;
                semester.appendChild(option);
            });
            
            fetchAPIRespone.data.semester.forEach(semesterItem  => {
                const option = document.createElement('option');
                option.value = semesterItem.semesterID;
                option.textContent = semesterItem.semesterName;
                searchSemester.appendChild(option);
            });

            fetchAPIRespone.data.schoolYear.forEach(year  => {
                const option = document.createElement('option');
                option.value = year;
                option.textContent = year;
                searchSchoolYear.appendChild(option);
            });
        }
    }
});






