/**
 * 格式化積分為千分號表示法
 * @param {number} points - 積分數值
 * @returns {string} 格式化後的積分字符串
 */
export const formatPoints = (points) => {
  if (!points && points !== 0) return '0'
  return Number(points).toLocaleString('zh-TW')
}
