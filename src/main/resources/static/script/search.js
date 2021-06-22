window.onload = () => {
    let search = document.querySelector('#search');
    search.oninput = function () {
        let value = this.value.trim();
        if(value === '') {
            location.replace("/search");
        }
        else{
            location.replace("/search?searchRequest="+value);
        }
    }
};