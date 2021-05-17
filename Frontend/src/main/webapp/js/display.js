/* [Theme toggler]
    + example: https://www.w3schools.com/css/tryit.asp?filename=trycss3_var_js 
    + color picker: https://colorschemedesigner.com/csd-3.5/ 

*/

let theme = "light";

// functions
function getRootStyle(property) {
    const root = document.querySelector(':root');
    const rootStyle = getComputedStyle(root);
    return rootStyle.getPropertyValue(property);
}

function setRootStyle(property, value) {
    const root = document.querySelector(':root');
    root.style.setProperty(property, value);
}

function setLightTheme() {
    setRootStyle("--bg-color", "#57bdff");
    setRootStyle("--container-bg-color", "#619abf");
    setRootStyle("--text-color", "#000");
    setRootStyle("--title-color", "#a63d19");

    theme = "light";
}

function setDarkTheme() {
    setRootStyle("--bg-color", "#57bdff");
    setRootStyle("--container-bg-color", "#619abf");
    setRootStyle("--text-color", "#000");
    setRootStyle("--title-color", "#a63d19");

    theme = "dark";
}

function toggleTheme() {
    if(theme == "light") {
        setDarkTheme();
    } else if(theme == "dark") {
        setLightTheme();
    } else {
        console.log(`error: theme (${theme}) not supported.`);
    }
}

// set theme on load
