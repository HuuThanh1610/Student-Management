
// button on table Class information
/* <td>
    <button class="button button-edit" title="Edit"
        onclick="openFormCreate('Update a class','1')"><i
            class="fa fa-edit"></i></button>
    <button class="button button-delete" title="Delete"
        onclick="openConfirmDelete('10A1','1')"><i
            class="fa fa-trash"></i></button>
</td> */


async function appendClass(dataRespone, pageIndex) {
    const current_page = pageIndex;
    const item_per_page = 5;

    // Duyệt qua danh sách và tạo hàng
    const tbody = document.getElementById('table-body');
    while (tbody.firstChild) {
        tbody.removeChild(tbody.firstChild);
    }

    let ListClass = dataRespone;
    if (ListClass == undefined) {
        const tr = document.createElement('tr');
        tr.innerHTML = `<td colspan="7">No class found.</td>`;
        tbody.appendChild(tr);
        return;
    }


    for (let i = 0; i < item_per_page; i++) {
        let index = current_page * item_per_page;
        if (ListClass[i + (index - item_per_page)] == undefined) break;
        const tr = document.createElement('tr'); // Tạo một hàng mới
        // Set data-student-id into <tr>
        tr.setAttribute('data-class-id', ListClass[i + (index - item_per_page)].classNameID);
        tr.innerHTML = `
<td>${1 + i + (index - item_per_page)}</td>
<td>${ListClass[i + (index - item_per_page)].className}</td>
<td>${ListClass[i + (index - item_per_page)].teacherName}</td>
<td>${ListClass[i + (index - item_per_page)].year}</td>
<td>
    <button class="button button-edit" title="Edit"
          onclick="openFormCreate('Update a class','${ListClass[i + (index - item_per_page)].classNameID}')"><i
          class="fa fa-edit"></i></button>
    <button class="button button-delete" title="Delete"
        onclick="openConfirmDelete('${ListClass[i + (index - item_per_page)].className}','${ListClass[i + (index - item_per_page)].classNameID}')"><i
        class="fa fa-trash"></i></button>
</td>
`;
        tbody.appendChild(tr);
    }



    let totalItems = ListClass.length;
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
    let dataRespone = await fetchClasses(searchClassName.value.trim(), searchSchoolYear.value.trim());
    if (dataRespone != null && dataRespone.status == 200) {
        appendClass(dataRespone.data, pageIndex);
    }
}


// Open popup confirm delete
async function openConfirmDelete(className, classNameID) {
    document.getElementById("confirmDelete").style.display = "block";
    let titleDelete = `Do you want to delete class <span style="color:blue">${className}</span>?`;
    document.getElementById("titlePopupDelete").innerHTML = titleDelete;
    const buttonYesDelete = document.getElementById('buttonYesDelete');
    const newButton = buttonYesDelete.cloneNode(true);
    buttonYesDelete.parentNode.replaceChild(newButton, buttonYesDelete);
    newButton.addEventListener('click', async function () {
        console.log(className, classNameID);
        let deleteAPIRespone = new Object(await deleteClass(classNameID));
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
async function openFormCreate(title, idClass) {
    document.getElementById("formPopupCreate").style.display = "block";
    document.getElementById("title-form-create-edit").innerHTML = title;
    const form = document.getElementById("formcontainer");

    if (idClass.length > 0) {
        let classData;
        let fetchAPIRespone = new Object(await fetchClassByID(idClass));
        if (fetchAPIRespone != null && fetchAPIRespone.status == 200) {
            classData = fetchAPIRespone.data[0];
        } else {
            throw new Error("Cannot fetch Student Data.");
        }
        form.setAttribute("data-mode", "update");
        console.log();
        classID.value = classData.classNameID;
        className.value = classData.className;
        homeroomTeacher.value = classData.teacherID;
        schoolYear.value = classData.year;
    } else {
        form.setAttribute("data-mode", "create");
    }
}


// Close popup form create - update
function closeFormCreate() {
    document.getElementById("formPopupCreate").style.display = "none";
    document.getElementById("formcontainer").reset();
}
