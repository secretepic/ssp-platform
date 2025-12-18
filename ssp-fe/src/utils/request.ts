import axios, {
  type AxiosInstance,
  type AxiosRequestConfig,
  type AxiosResponse,
  type AxiosError
} from 'axios'
import { ElMessage } from 'element-plus'

class Http {
  // Axios 实例
  private instance: AxiosInstance

  // 基础配置
  private baseConfig: AxiosRequestConfig = {
    baseURL: import.meta.env.VITE_APP_API_URL || '/sn',
    timeout: 10000,
    headers: {
      'Content-Type': 'application/json;charset=utf-8'
    }
  }

  constructor(config: AxiosRequestConfig = {}) {
    // 合并配置
    this.instance = axios.create({ ...this.baseConfig, ...config })

    // 请求拦截器
    this.instance.interceptors.request.use(
      (config) => {
        // 添加Token
        const token = localStorage.getItem('token')
        if (token) {
          config.headers.Authorization = `Bearer ${token}`
        }

        return config
      },
      (error: AxiosError) => {
        this.handleError(error)
        return Promise.reject(error)
      }
    )

    // 响应拦截器
    this.instance.interceptors.response.use(
      (response: AxiosResponse) => {
        // 直接返回响应数据中的data属性
        return response.data
      },
      (error: AxiosError) => {
        this.handleError(error)
        return Promise.reject(error)
      }
    )
  }

  // 错误处理
  private handleError(error: AxiosError) {
    if (error.message.includes('timeout')) {
      ElMessage.error('请求超时，请稍后再试')
    } else if (error.response) {
      const status = error.response.status
      switch (status) {
        case 401:
          ElMessage.error('未授权，请重新登录')
          // 可以在这里跳转到登录页
          break
        case 403:
          ElMessage.error('拒绝访问')
          break
        case 404:
          ElMessage.error('请求地址不存在')
          break
        case 500:
          ElMessage.error('服务器内部错误')
          break
        default:
          ElMessage.error(`请求错误: ${status}`)
      }
    } else if (error.request) {
      ElMessage.error('网络错误，请检查网络连接')
    } else {
      ElMessage.error('请求失败，请稍后再试')
    }
  }

  // 通用请求方法 - 使用async/await优化
  public async request<T = any>(config: AxiosRequestConfig): Promise<T> {
    const response = await this.instance.request<T>(config)
    return response.data
  }

  // GET请求
  public get<T = any>(url: string, config?: AxiosRequestConfig): Promise<T> {
    return this.request<T>({ ...config, method: 'get', url })
  }

  // POST请求
  public post<T = any>(
    url: string,
    data?: any,
    config?: AxiosRequestConfig
  ): Promise<T> {
    return this.request<T>({ ...config, method: 'post', url, data })
  }

  // PUT请求
  public put<T = any>(
    url: string,
    data?: any,
    config?: AxiosRequestConfig
  ): Promise<T> {
    return this.request<T>({ ...config, method: 'put', url, data })
  }

  // DELETE请求
  public delete<T = any>(url: string, config?: AxiosRequestConfig): Promise<T> {
    return this.request<T>({ ...config, method: 'delete', url })
  }

  // 文件上传
  public upload<T = any>(
    url: string,
    file: File,
    config?: AxiosRequestConfig
  ): Promise<T> {
    const formData = new FormData()
    formData.append('file', file)

    return this.request<T>({
      ...config,
      method: 'post',
      url,
      data: formData,
      headers: {
        'Content-Type': 'multipart/form-data'
      }
    })
  }
}

// 导出实例
export default new Http()
