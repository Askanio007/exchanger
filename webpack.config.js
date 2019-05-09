var path = require("path");
module.exports = {
    entry: ["./frontend/app.tsx"],
    output: {
        path: path.resolve(__dirname, "./src/main/resources/static"),
        filename: "bundle.js"
    },
    resolve: {
        extensions: [".ts", ".tsx", ".js"]
    },

    module: {
        rules: [
            {
                test: /\.tsx?$/,
                loader: "ts-loader",
                exclude: /node_modules/
            }
        ]
    }
};