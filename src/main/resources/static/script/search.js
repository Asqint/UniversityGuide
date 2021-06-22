window.onload = () => {
    let search = document.querySelector('#search');
    search.oninput = function () {
        let value = this.value.trimLeft();
        if(value === '') {
            location.replace("/search");
        }
        else{
            if(value.includes(' ')) {
                value = value.replace(/ /g,'+');
            }
            location.replace("/search?searchRequest="+value);
        }
    }
};