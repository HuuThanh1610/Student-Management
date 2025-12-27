function openUserAccount() {
    const dropdown = document.getElementById("dropdownAccount-content");
    if ( dropdown.style.display === "block"){
        dropdown.style.display = "none";
    }else{
        dropdown.style.display = "block";
    }
  }
  
  // Close the dropdown if the user clicks outside of it
  window.addEventListener("click", function(event) {
    const btn = document.querySelector(".dropbtnAccount");
    const dropdown = document.getElementById("dropdownAccount-content");
    if(event.target !== btn && !btn.contains(event.target)){
        dropdown.style.display = "none";
    }
  });