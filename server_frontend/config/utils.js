const path = require('path');
const MiniCssExtractPlugin = require('mini-css-extract-plugin');
const ROOT = path.resolve(__dirname, '..');
const root = path.join.bind(path, ROOT);

exports.root = root;

exports.cssLoaders = function (options = {}) {
  const cssLoader = {
    loader:'css-loader',
  };
  function generateLoaders(loader,loaderOptions){
    let loaders = [cssLoader];
    loaders.push({
      loader: 'postcss-loader',
      options: {
        ident:'postcss',
        plugins: (loader)=> [
          require('postcss-import')({root: loader.resourcePath}),
          require('autoprefixer')(),
        ]
      }

    })
    if(loader){
      loaders.push({
        loader: loader + '-loader',
        options: Object.assign({},loaderOptions,{
          sourceMap: options.sourceMap,
        })
      })
    }
    if(options.extract){
      return [MiniCssExtractPlugin.loader].concat(loaders);
    }
    else {
      return ['vue-style-loader'].concat(loaders);
    }
  }
  return {
    css: generateLoaders(),
    less: generateLoaders('less'),
    sass: generateLoaders('sass',{indentedSyntax:true}),
    scss: generateLoaders('sass', {outputStyle: 'compressed'}),
  }
}

// Generate loaders for standalone style files (outside of .vue)
exports.styleLoaders = function(options) {
  let output = [];
  const loaders = exports.cssLoaders(options);
  for (let extension in loaders) {
    if (Object.prototype.hasOwnProperty.call(loaders, extension)) {
      let loader = loaders[extension];
      output.push({
        test: new RegExp('\\.' + extension + '$'),
        use: loader,
      });
    }
  }
  return output;
};
