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


// Lấy phần tử form
const form = document.getElementById('formcontainer');
const classPattern = /^(10|11|12)A([1-9]|1[0-9])$/;
const className = document.getElementById('className');
const homeroomTeacher = document.getElementById('homeroomTeacher');
const schoolYear = document.getElementById('schoolYear');
const classID = document.getElementById('classID');


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

// ------------------------------------------------------------------------------//
// Bắt sự kiện khi submit form 
form.addEventListener('submit', async function (event) {
    // Ngăn không cho form tự động gửi đi
    event.preventDefault();

    // Get data-mode
    const mode = form.getAttribute("data-mode");


    if (mode == "create") {
        // Validate Class
        if (!validateClassName(className)) {
            alert("Invalid Class name.");
            return;
        }

        // Validate Homeroom Teacher  
        if (homeroomTeacher.value == '') {
            alert("Invalid Homeroom Teacher.");
            return;
        }

        // Validate School year
        if (schoolYear.value == '') {
            alert("Please choose school year.");
            return;
        }

        console.log('Class name: ', className.value);
        console.log('Homeroom teacher: ', homeroomTeacher.value);
        console.log('School year: ', schoolYear.value);
        alert('Submit success.');

        let createAPIRespone = new Object(await createClass(className.value, homeroomTeacher.value, schoolYear.value));
        if (createAPIRespone != null) {
            console.log(createAPIRespone.data);
            alert(createAPIRespone.message);
            if (createAPIRespone.status === 200) {
                loadingItems(1);
                resetFormSubmitbtn();
                closeFormCreate();
            }
        }
    } else{
        if (!classID.value) {
            alert("Class ID is required for update.");
            return;
        }
        let updateAPIRespone = new Object(await updateClass(classID.value, className.value, homeroomTeacher.value, schoolYear.value));
        if (updateAPIRespone != null) {
            console.log(updateAPIRespone.data);
            alert(updateAPIRespone.message);
            if (updateAPIRespone.status === 200) {
                loadingItems(1);
                closeFormCreate();
            }
        }
    }
// if (mode === "update")  
});


// Validate class name
function validateClassName(className) {
    if (className.value == '') {
        return false;
    }

    if (!classPattern.test(className.value)) {
        return false;
    }
    return true;
}

// Validate Homeroom Teacher
function validateHomeroomTeacher(homeroomTeacher) {
    if (homeroomTeacher.value.length < 3 || homeroomTeacher.value.length > 20) {
        return false;
    }
    return true;
}

//Reset form when submit
function resetFormSubmitbtn() {
    document.getElementById("formcontainer").reset();
}

// Change text color when user change current text
className.addEventListener('change', function (event) {
    className.style.color = 'red';
});

homeroomTeacher.addEventListener('change', function (event) {
    homeroomTeacher.style.color = 'red';
});

schoolYear.addEventListener('change', function (event) {
    schoolYear.style.color = 'red';
});


// Change text color when user change current text
//------------------------------------------------------------------------------//






//----------------------------Validate input value into search box----------------------------//
// Lấy phần tử từ search box
const searchClassName = document.getElementById('searchClassName');
const searchSchoolYear = document.getElementById('searchSchoolYear');
const btnSearch = document.getElementById('btnSearch');

// Bắt sự kiện click on button search
btnSearch.onclick = async function () {
    const className = searchClassName.value.trim();
    const schoolYear = searchSchoolYear.value.trim();

    console.log('Class name to seacrh: ', className);
    console.log('School year to search: ', schoolYear);
    // searchClassName.value = '-- Choose class --';
    // searchSchoolYear.value = '-- Choose school year --';
    enableLoading();

    let searchAPIRespone = new Object(await fetchClasses(className, schoolYear));
    if (searchAPIRespone != null) {
        appendClass(searchAPIRespone.data, 1);
    }

    disableLoading();
}

//----------------------------------------------------------------------------------------//

//------------------------------------------API Class Managerment----------------------------------------------//


//function send request create student
async function createClass(className, teacherID, shooclYear) {
    const url = 'http://localhost:50/StudentManagement/CreateClass';

    const data = `className=${encodeURIComponent(className)}
    &teacherID=${encodeURIComponent(teacherID)}
    &shooclYear=${encodeURIComponent(shooclYear)}`;

    try {
        const response = await fetch(url, {
            method: 'POST',
            headers: {
                'Content-Type': 'application/x-www-form-urlencoded'
            },
            body: data
        });

        if (!response.ok) {
            throw new Error('Failed to create class');
        }
        return await response.json();

    } catch (error) {
        console.error('Error:', error);
    }
}

//function send request update class
async function updateClass(classID, className, teacherID, shooclYear) {
    const url = 'http://localhost:50/StudentManagement/UpdateClass';

    const data = `classID=${encodeURIComponent(classID)}
    &className=${encodeURIComponent(className)}
    &teacherID=${encodeURIComponent(teacherID)}
    &shooclYear=${encodeURIComponent(shooclYear)}`;


    try {
        const response = await fetch(url, {
            method: 'POST',
            headers: {
                'Content-Type': 'application/x-www-form-urlencoded'
            },
            body: data
        });

        if (!response.ok) {
            throw new Error('Failed to update class');
        }
        return await response.json();

    } catch (error) {
        console.error('Error:' + error);
    }
}


//function fetch Data - search class
async function fetchClasses(nameClass, shooclYear) {
    try {
        const url = `http://localhost:50/StudentManagement/SearchClass?className=${encodeURIComponent(nameClass)}&shooclYear=${encodeURIComponent(shooclYear)}`;

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
        return [];
    }
}

//fetch Class by classID
async function fetchClassByID(classID) {
    try {
        const url = `http://localhost:50/StudentManagement/GetByClassID?classID=${encodeURIComponent(classID)}`;

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

//function send request delete class
async function deleteClass(classID) {
    const url = 'http://localhost:50/StudentManagement/DeleteClass';

    const data = `classID=${encodeURIComponent(classID)}`;


    try {
        const response = await fetch(url, {
            method: 'POST',
            headers: {
                'Content-Type': 'application/x-www-form-urlencoded'
            },
            body: data
        });

        if (!response.ok) {
            throw new Error('Failed to delete class.');
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
    defaultOption4.textContent = "-- Choose teacher --";
    homeroomTeacher.prepend(defaultOption4);

    let fetchAPIRespone = await fetchDataDropdown();
    if (fetchAPIRespone != null) {
        console.log(fetchAPIRespone.data);
        if (fetchAPIRespone.status === 200) {
            fetchAPIRespone.data.teacher.forEach(teacherItem  => {
                const option = document.createElement('option');
                option.value = teacherItem.TeacherID;
                option.textContent = teacherItem.TeacherName;
                homeroomTeacher.appendChild(option);
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
            fetchAPIRespone.data.schoolYear.forEach(year  => {
                const option = document.createElement('option');
                option.value = year;
                option.textContent = year;
                searchSchoolYear.appendChild(option);
            });
        }
    }
});






