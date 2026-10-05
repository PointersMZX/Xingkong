// build.rs — 接力 2 启用 aidl-rs 生成 AIDL Rust 源
// 当前骨架不生成，留空
fn main() {
    // TODO 接力 2：
    //   1. 调用 aidl-rs 解析 ../shared-aidl/src/main/aidl/com/xingkong/monitor/shared/ipc/*.aidl
    //   2. 生成 Rust 模块到 OUT_DIR/com/xingkong/monitor/shared/ipc/
    //   3. 通过 cargo:rustc-env=OUT_DIR 传递
    println!("cargo:rerun-if-changed=../shared-aidl/src/main/aidl");
}
