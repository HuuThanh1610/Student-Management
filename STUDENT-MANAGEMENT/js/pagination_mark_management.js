
// button on table Class information
/* <td>
    <button class="button button-edit" title="Edit"
        onclick="openFormCreate('Update a class','1')"><i
            class="fa fa-edit"></i></button>
    <button class="button button-delete" title="Delete"
        onclick="openConfirmDelete('10A1','1')"><i
            class="fa fa-trash"></i></button>
</td> */




async function appendGrade(dataRespone, pageIndex) {
    const current_page = pageIndex;
    const item_per_page = 5;
    let index = current_page * item_per_page;

    // Duyệt qua danh sách và tạo hàng
    const tbody = document.getElementById('table-body');
    while (tbody.firstChild) {
        tbody.removeChild(tbody.firstChild);
    }

    if (dataRespone == undefined) {
        const tr = document.createElement('tr');
        tr.innerHTML = `<td colspan="8">No grade found.</td>`;
        tbody.appendChild(tr);
        return;
    }
    let ListMark = dataRespone;
    for (let i = 0; i < item_per_page; i++) {
        if (ListMark[i + (index - item_per_page)] == undefined) break;
        const tr = document.createElement('tr'); // Tạo một hàng mới
        tr.innerHTML = `
    <td>${1 + i + (index - item_per_page)}</td>
    <td>${ListMark[i + (index - item_per_page)].className}</td>
    <td>${ListMark[i + (index - item_per_page)].studentName}</td>
    <td>${ListMark[i + (index - item_per_page)].subjectName}</td>
    <td>${ListMark[i + (index - item_per_page)].markOfSubject}</td>
    <td>${ListMark[i + (index - item_per_page)].semester}</td>
    <td>${ListMark[i + (index - item_per_page)].year}</td>
    <td>
        <button class="button button-edit" title="Edit"
              onclick="openFormCreate('Update mark','${ListMark[i + (index - item_per_page)].markID}')"><i
              class="fa fa-edit"></i></button>
        <button class="button button-delete" title="Delete" id="buttonDelete"
            onclick="openConfirmDelete('${ListMark[i + (index - item_per_page)].markID}','${ListMark[i + (index - item_per_page)].studentName}','${ListMark[i + (index - item_per_page)].subjectName}','${ListMark[i + (index - item_per_page)].semester}','${ListMark[i + (index - item_per_page)].year}')"><i
            class="fa fa-trash"></i></button>
    </td>
    `;
        tbody.appendChild(tr);
        // Create title popup delete ---- const titlePopupDelete = 
        // document.getElementById('titlePopupDelete').innerHTML = titleDelete;
    }



    let totalItems = ListMark.length;
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

async function loadingItems(pageIndex) { 
    let dataRespone = await fetchGrade(searchStudentName.value.trim(), searchClassName.value.trim(), searchSubject.value.trim(), searchSemester.value.trim(), searchSchoolYear.value.trim());
    if (dataRespone != null && dataRespone.status == 200) {
        appendGrade(dataRespone.data, pageIndex);
    }
}

// Open popup confirm delete
function openConfirmDelete(markID, name, subject, semester, year) {
    document.getElementById("confirmDelete").style.display = "block";
    let titleDelete = `Do you want to delete grade <span style="color:blue">${subject}</span> - <span style="color:blue">${semester}</span>
     - <span style="color:blue">${year}</span> of student <span style="color:blue">${name}</span>?`;
    document.getElementById("titlePopupDelete").innerHTML = titleDelete;
    const buttonYesDelete = document.getElementById('buttonYesDelete');
    const newButton = buttonYesDelete.cloneNode(true);
    buttonYesDelete.parentNode.replaceChild(newButton, buttonYesDelete);
    newButton.addEventListener('click', async function () {
        console.log(markID);
        let deleteAPIRespone = await deleteGrade(markID);
        // debugger;
        if (deleteAPIRespone != null) {
            alert(deleteAPIRespone.message);
            if (deleteAPIRespone.status === 200) {
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
async function openFormCreate(title, markID) {
    document.getElementById("formPopupCreate").style.display = "block";
    document.getElementById("title-form-create-edit").innerHTML = title;
    const searchSection = document.getElementById("searchInFormCreate");
    const updateSectionStudentName = document.getElementById("updateSectionStudentName");
    const updateSectionReason = document.getElementById("updateSectionReason");
    if (markID.length > 0) {
        let gradeData;
        let searchAPIRespone = new Object(await fetchGradeByMarkID(markID));
        if (searchAPIRespone != null && searchAPIRespone.status == 200) {
            gradeData = searchAPIRespone.data[0];
        } else {
            throw new Error("Cannot fetch grade Data.");
        }
        formPopupCreate.setAttribute("data-mode", "update");
        searchSection.style.display = "none";
        updateSectionStudentName.style.display = "block";
        studentName.style.display = "block";
        updateSectionReason.style.display = "block";
        reason.style.display = "block";
        markIDForm.value = gradeData.markID;
        studentName.value = gradeData.studentName;
        subject.value = gradeData.subjectID;
        mark.value = gradeData.markOfSubject;
        semester.value = gradeData.semesterID;
        // Disable other fields
        studentName.setAttribute("disabled", "true");
        subject.setAttribute("disabled", "true");
        mark.setAttribute("required", "true");
        semester.setAttribute("disabled", "true");
        reason.setAttribute("required", "true");
    } else {
        formPopupCreate.setAttribute("data-mode", "create");
        searchSection.style.display = "block";
        updateSectionStudentName.style.display = "none";
        studentName.style.display = "none";
        updateSectionReason.style.display = "none";
        reason.style.display = "none";
        // Reset and enable all fields for create mode
        studentName.removeAttribute("disabled");
        mark.setAttribute("required", "true");
        subject.removeAttribute("disabled");
        semester.removeAttribute("disabled");
    }
}



// Close popup form create - update
function closeFormCreate() {
    document.getElementById("formPopupCreate").style.display = "none";
    document.getElementById("formcontainer").reset();
}




