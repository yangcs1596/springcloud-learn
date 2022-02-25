# vue bootstrap admin

## Project setup
```
npm install
```

### Compiles and hot-reloads for development
```
npm run serve
```

### Compiles and minifies for production
```
npm run build
```

### Lints and fixes files
```
npm run lint
```

### Customize configuration
See [Configuration Reference](https://cli.vuejs.org/config/).

## snapshoot
## simple 
###dark
![](<./doc/imgs/dark-full.png>)
![](<./doc/imgs/dark-mini.png>)

###light
![](<./doc/imgs/light-full.png>)
![](<./doc/imgs/light-mini.png>)
## muti menu dark
![](<./doc/imgs/muti-dark-full.png>)
![](<./doc/imgs/muti-dark-mini.png>)

## 使用方法

### simple layout
```vue
 <simple-layout :menus="routes"/>
```
#### 
### muti layout
```vue
<multi-layout :menus="routes" multi-expand/>
```

```js
export default {
  name: 'App',
  data () {
    return {
      routes: this.$router.options.routes,
    }
  }
}
```



