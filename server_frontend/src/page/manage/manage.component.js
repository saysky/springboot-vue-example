export default {
    data() {
        return {
            appOptions: [],
            appValue: '',
        }
    },
    created() {
        // this.getApp();
    },
    methods: {
        save(url, params, visible) {
            this.$http.post(url, params).then(({body}) => {
                if (body.success === true) {
                    this.$message.success(body.message);
                    this.queryList();
                    if (visible !== '') {
                        this[visible] = false;
                    }
                } else this.$message.error(body.message);
            }).catch(() => {
                this.$message.error('操作失败');
            })
        },
        handleSizeChange(val) {
            this.pagination.pageSize = val;
            this.pagination.pageIndex = 1;
            this.queryList();
        },
        handleCurrentChange(val) {
            this.pagination.pageIndex = val;
            this.queryList();
        },
        delete(url, params) {
            this.$confirm('该信息删除后无法恢复，确定要删除吗？', '提示', {
                confirmButtonText: '确定',
                cancelButtonText: '取消',
                type: 'warning',
            }).then(() => {
                this.$http.post(url, {}, {params: params}).then(({body}) => {
                    if (body.success === true) {
                        this.$message.success(body.message);
                        this.queryList();
                    } else {
                        this.$message.error(body.message);
                    }
                }).catch(() => {
                    this.$message.error('删除失败');
                })
            }).catch(() => {
                this.$message.info('已取消删除')
            })
        },
    }
}
