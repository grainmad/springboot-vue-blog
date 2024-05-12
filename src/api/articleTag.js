import axiosInstall from './axiosInstance';

//通过标签找分页查询文章
export function getArticleByTag(articleTag) {
    return axiosInstall.post('front/article/getArticleByTag', articleTag)
}
