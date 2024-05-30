/** @type {import('tailwindcss').Config} */

export const content = [
    "./src/**/*.{html,ts}",
];

export const theme = {
    extend: {
        colors: {
            "primary": "#0df4e2",
            "accent": "#e20d74",
            "warn": "#e20d0d",
            "main": "#201b31",
            "secondary": "#2c2745",
            "tertiary": "#413955",
            "card": "#37304a",
            "divider": "#4f4b60",
            "mat-hover": "#464059",
            "available": "#49f030",
            "unavailable": "#6a6580",
            "mat-primary-hover": "#3c4157"
        },
        spacing: {
            "26": "110px",
        },
        flexGrow: {
            "0.6": "0.6"
        },
        screens: {
            "mm": "320px",
            "ml": "425px",
            "md": "900px",
            "sm": "580px",
            "tb": "768px",
            "lm": "1024px",
            "ll": "1440px",
            "feed-md": "1142px",
            "feed-sm": "838px",

            "p-xl": "1526px",

            "f-xs": "375px",
            "f-s": "472px",

            "r-s": "576px",
            "r-sm": "732px",
            "r-sl": "1000px",
            "r-md": "1400px",
            "r-l": "1740px",
            "r-xl": "1970px",
            "r-xxl": "2560px",
        },
        borderWidth: {
            "1": "1px"
        }
    }
};

export const plugins = [];
