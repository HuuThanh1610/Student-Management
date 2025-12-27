// button on table Student information
/* <td>
<button class="button button-edit" title="Edit"
    onclick="openFormCreate('Update a student','10')"><i
        class="fa fa-edit"></i></button>
<button class="button button-delete" title="Delete"
    onclick="openConfirmDelete('Nguyễn Hữu Thành','10')"><i
        class="fa fa-trash"></i></button>
</td> */

// Create object Student
// function Student(no, className, identification, name, gender, birthday) {
//     this.no = no;
//     this.className = className;
//     this.identification = identification;
//     this.name = name;
//     this.gender = gender;
//     this.birthday = birthday;
// }
function appendStudent(dataRespone, pageIndex) {

    // Create List Student
    const current_page = pageIndex;
    const item_per_page = 5;

    // Duyệt qua danh sách và tạo hàng
    const tbody = document.getElementById('table-body');
    while (tbody.firstChild) {
        tbody.removeChild(tbody.firstChild);
    }

    if (dataRespone == undefined) {
        const tr = document.createElement('tr');
        tr.innerHTML = `<td colspan="7">No students found.</td>`;
        tbody.appendChild(tr);
        return;
    }
    let listStudent = dataRespone.filter(student => student.status === 'Active');
    for (let i = 0; i < item_per_page; i++) {
        let index = current_page * item_per_page;
        if (listStudent[i + (index - item_per_page)] == undefined) break;
        if (listStudent[i + (index - item_per_page)].status == 'Active') {
            const tr = document.createElement('tr'); // Tạo một hàng mới
            // Set data-student-id into <tr>
            tr.setAttribute('data-student-id', listStudent[i + (index - item_per_page)].studentID);
            tr.innerHTML = `
    <td>${1 + i + (index - item_per_page)}</td>
    <td>${listStudent[i + (index - item_per_page)].className}</td>
    <td>${listStudent[i + (index - item_per_page)].identification}</td>   
    <td>${listStudent[i + (index - item_per_page)].name}</td>
    <td>${listStudent[i + (index - item_per_page)].gender}</td>
    <td>${listStudent[i + (index - item_per_page)].dateOfBirth}</td>
    <td>
        <button class="button button-edit" title="Edit"
              onclick="openFormCreate('Update a student','${listStudent[i + (index - item_per_page)].studentID}')"><i
              class="fa fa-edit"></i></button>
        <button class="button button-delete" title="Delete"
            onclick="openConfirmDelete('${listStudent[i + (index - item_per_page)].name}','${listStudent[i + (index - item_per_page)].studentID}')"><i
            class="fa fa-trash"></i></button>
    </td>
`;
            tbody.appendChild(tr);
        }

    }

    let totalItems = listStudent.length;
    let pagesNumber = Math.ceil(totalItems / item_per_page);


    let htmlPage = '<a href="#" >&laquo;</a>';
    for (let i = 1; i <= pagesNumber; i++) {
        let activeClass = "";
        if (current_page == i) {
            activeClass = 'take-current-page'
        }
        htmlPage += '<a href="#" onclick="loadingItems(' + i + ')" class="' + activeClass + '" data-index=' + i + ' >' + i + '</a>';
    }
    htmlPage += '<a href="#">&raquo;</a>';
    document.getElementById('pagination').innerHTML = htmlPage;
}

//loadingItems
async function loadingItems(pageIndex) {
    let dataRespone = await fetchStudents(searchStudentName.value, searchIdentification.value, searchClassName.value);
    if (dataRespone != null && dataRespone.status == 200) {
        appendStudent(dataRespone.data, pageIndex);
    }
}


// Open popup confirm delete
async function openConfirmDelete(name, studentID) {
    document.getElementById("confirmDelete").style.display = "block";
    let titleDelete = `Do you want to delete student <span style="color:blue">${name}</span>?`;
    document.getElementById("titlePopupDelete").innerHTML = titleDelete;
    const buttonYesDelete = document.getElementById('buttonYesDelete');
    const newButton = buttonYesDelete.cloneNode(true);
    buttonYesDelete.parentNode.replaceChild(newButton, buttonYesDelete);
    newButton.addEventListener('click', async function () {
        if(reason.value == ''){
            alert('Please fill in the reason for deleting the student.');
            return;
        }
        let deleteAPIRespone = new Object(await deleteStudent(studentID, reason.value.trim()));
        if (deleteAPIRespone != null) {
            reason.value = '';
            alert(deleteAPIRespone.message);
            if (deleteAPIRespone.status === 200) {
               // window.location.reload();
               loadingItems(1);
            }
        }
    })
}

// Close popup confirm delete
function closeConfirmDelete() {
    document.getElementById("confirmDelete").style.display = "none";
}


// Create students
// Open popup form create -
async function openFormCreate(title, idStudent) {
    document.getElementById("formPopupCreate").style.display = "block";
    document.getElementById("title-form-create-edit").innerHTML = title;
    const form = document.getElementById("formcontainer");

    if (idStudent.length > 0) {
        let studentData;
        let searchAPIRespone = new Object(await fetchStudentByID(idStudent));
        if (searchAPIRespone != null && searchAPIRespone.status == 200) {
            studentData = searchAPIRespone.data[0];
        } else {
            throw new Error("Cannot fetch Student Data.");
        }
        form.setAttribute("data-mode", "update");
        studentID.value = studentData.studentID;
        studentName.value = studentData.name;
        identification.value = studentData.identification;
        email.value = studentData.email;
        genderStudent.value = studentData.gender;
        className.value = studentData.className;
        schoolYear.value = studentData.schoolYear;
        addressStudent.value = studentData.address;
        phoneNumber.value = studentData.phoneNumber;
        dateOfBirthStudent.value = convertDateFormat(studentData.dateOfBirth);
    } else {
        form.setAttribute("data-mode", "create");
    }
}


// Close popup form create - update
function closeFormCreate() {
    document.getElementById("formPopupCreate").style.display = "none";
    document.getElementById("formcontainer").reset();
}


//function convert date format
function convertDateFormat(dateString) {
    const [year, month, day] = dateString.split('-');
    const formattedDate = `${year}-${month}-${day}`;
    return formattedDate;
}
