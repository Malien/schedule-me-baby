importScripts("https://cdn.jsdelivr.net/npm/idb-keyval@6/dist/umd.js")

const restrictedRoutes = new Set(["/schedule"])
const authedOrigin = "http://localhost:8080"

self.addEventListener("fetch", (ev) => {
    ev.respondWith((async () => {
        // const idb = await idbPromise
        const token = await idbKeyval.get("token")
        const  { request } = ev;
        const { pathname, host, protocol } = new URL(request.url);
        const strippedPathname = pathname.replace(/\/$/, '')
        if (`${protocol}//${host}` !== authedOrigin) {
            return fetch(request)
        }
        if (!token && restrictedRoutes.has(strippedPathname)) {
            return Response.redirect("/login")
        } else {
            const headers = new Headers(request.headers)
            if (token) {
                headers.append("Authorization", `Bearer ${token}`)
            }
            const res = await fetch(new Request(request, { headers }))
            if (res.status === 401) {
                idbKeyval.del("token")
                return Response.redirect("/login")
            }
            return res
        }
    })())
})